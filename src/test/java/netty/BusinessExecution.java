package netty;
 
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
 
import baselib.CmbPacket;
import core.BusinsContext;
import core.log.ILogger;
import core.log.LogUtils;
import core.netty.channel.ChannelFutureListener;
import core.netty.channel.ChannelHandlerContext;
import core.netty.channel.SimpleChannelInboundHandler;
import frame.Request;
import frame.Response;
import server.MidConversion;
import server.Platform;
 
/**
 * @author 80374311 业务处理类
 * update by 80374311 2016-05-05 for reason:writeAndFlush后面增加close主动关闭TCP连接。
 */
public class BusinessExecution extends
		SimpleChannelInboundHandler<ChannelBindData> {
 
	private static ILogger log = LogUtils.getLogger(BusinessExecution.class);
 
	private Executor executor;
 
	public BusinessExecution(Executor executor) {
		super();
		this.executor = executor;
	}
 
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ChannelBindData msg)
			throws Exception {
		// 1.托管核心Request请求消息，调业务组件执行
		if (msg.isRequest()) {
			try {
				process(ctx, msg);
			} catch (Throwable th) {
				// 线程池管理类抛出的错误处理
				ctx.writeAndFlush(
						ChannelBindData.createResponse(Response
								.createFailed(th))).addListener(
						ChannelFutureListener.CLOSE);
			}
		}
		// 2.托管银行CmbPacket请求消息，调业务组件执行
		else if (msg.isRequestPacket()) {
			try {
				process(ctx, msg);
			} catch (Throwable th) {
				// 线程池管理类抛出的错误处理
				ctx.writeAndFlush(
						ChannelBindData.createResponsePacket(
								MidConversion.getErrRespBytes(
										msg.isLatestVersion(), th),
								msg.isLatestVersion())).addListener(
						ChannelFutureListener.CLOSE);
			}
		}
		// 3.解码错误消息,直接写返回
		else if (msg.isResponse() || msg.isResponsePacket()) {
			ctx.writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
		}
 
		else {
			// unreachable
		}
	}
 
	private void process(ChannelHandlerContext ctx, ChannelBindData msg)
			throws Exception {
		try {
			executor.execute(new BusinessTask(ctx, msg));
		}
		// 任务队伍满，系统会拒绝请求
		catch (RejectedExecutionException e) {
			// wrapped exception, make it friendly.
			String errMsg = "因请求任务过多，系统已拒绝本次请求，请稍候再试，谢谢。";
			log.error(errMsg, e);
			throw new Exception(errMsg, e);
		} catch (Throwable th) {
			log.error("business thread running occur error.", th);
			throw th;
		}
	}
 
	/**
	 * 业务任务
	 * 
	 * @author 80374311
	 */
	private static class BusinessTask implements Runnable {
 
		ChannelBindData data;
 
		ChannelHandlerContext ctx;
 
		public BusinessTask(ChannelHandlerContext ctx, ChannelBindData data) {
			this.ctx = ctx;
			this.data = data;
		}
 
		public void run() {
			try {
 
				// 托管银行业务执行
				if (data.isRequestPacket()) {
					run0();
				}
				// 托管核心业务执行
				else {
					run1();
				}
			} finally {
				BusinsContext context = BusinsContext.getBusinessContext();
				context.clear(false);
			}
		}
 
		private void run0() {
			byte[] sndData = null;
			boolean isLatestVersion = data.isLatestVersion();
			try {
				// 返回结果
				CmbPacket req = data.getReqPacket();
				CmbPacket pk = (CmbPacket) Platform.getDispatcher().proc(req);
				if (pk == null) {
					sndData = CmbPacket.toByteArray("SUCC", null);
				} else {
					sndData = pk.toByteArray("SUCC");
				}
				if (!isLatestVersion) {
					sndData = MidConversion.ConvertResponse(sndData);
				}
				ctx.writeAndFlush(
						ChannelBindData.createResponsePacket(sndData,
								isLatestVersion)).addListener(
						ChannelFutureListener.CLOSE);
			} catch (Throwable th) {
				th = recordError(th);
				ctx.writeAndFlush(
						ChannelBindData.createResponsePacket(MidConversion
								.getErrRespBytes(isLatestVersion, th),
								isLatestVersion)).addListener(
						ChannelFutureListener.CLOSE);
			}
		}
 
		private void run1() {
			// boolNoWait不需要返回消息.
			Request req = data.getRequest();
			boolean boolNoWait = checkNoWait(req);
			try {
				Response res = (Response) Platform.getDispatcher().proc(req);
				if (!boolNoWait) {
					if (res == null) {
						res = Response.createSucceed();
					}
					ctx.writeAndFlush(ChannelBindData.createResponse(res))
							.addListener(ChannelFutureListener.CLOSE);
				}
			} catch (Throwable e) {
				e = recordError(e);
				if (!boolNoWait) {
					ctx.writeAndFlush(
							ChannelBindData.createResponse(Response
									.createFailed(e))).addListener(
							ChannelFutureListener.CLOSE);
				}
			}
		}
 
		private boolean checkNoWait(Request req) {
			boolean _isNoWait = false;
			byte[] bType = req.getBin("$WAITTYPE$", "WTTYPE", 0);
			if (bType != null && (bType[0] == 'Y')) {
				_isNoWait = true;
			}
			return _isNoWait;
		}
		
		// 写异常日志
		private Throwable recordError(Throwable e) {
			Throwable r = e;
			if (e instanceof BatchUpdateException) {
				BatchUpdateException b = (BatchUpdateException) e;
				SQLException realExp = b.getNextException();
				if (realExp != null) {
					log.error(e.getMessage());
					r = realExp;
				}
			}
			//有些业务异常可以不用记录日志
			//if (e instanceof BusinessException){}
			log.error(r);
			return r;
		}
	}
 
}
