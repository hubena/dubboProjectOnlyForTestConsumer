package netty;
 
import java.io.UnsupportedEncodingException;
 
import server.MidConversion;
import server.Platform;
import core.log.ILogger;
import core.log.LogUtils;
import core.netty.buffer.ByteBuf;
import core.netty.channel.ChannelHandlerContext;
import core.netty.handler.codec.MessageToByteEncoder;
import frame.Response;
 
/**
 * 业务编码,发送数据流
 * 
 * @author 80374311
 */
public class ProtocolEncoder extends MessageToByteEncoder<ChannelBindData> {
 
	private static ILogger log = LogUtils.getLogger(ProtocolEncoder.class);
 
	@Override
	protected void encode(ChannelHandlerContext ctx, ChannelBindData msg, ByteBuf out) {
		if (msg.isResponsePacket()) {
			encode0(ctx, msg, out);
		} else if (msg.isResponse()) {
			encode1(ctx, msg.getResponse(), out);
		} else {
			// unreachable
		}
	}
 
	/**
	 * 托管银行
	 * 
	 * @param ctx
	 * @param data
	 * @param out
	 */
	private void encode0(ChannelHandlerContext ctx, ChannelBindData data, ByteBuf out) {
		boolean isLatestVersion = data.isLatestVersion();
		try {
			if (data.getRespBytes() == null) {
				return;
			}
			// Convert the Response into a byte array.
			byte[] respBytes = data.getRespBytes();
 
			int dataLength = respBytes.length;
 
			// Write a message.
			out.writeInt(dataLength); // data length
 
			out.writeBytes(respBytes); // data
 
			logResponseData(respBytes);
 
		} catch (Throwable th) {
			// Write error message.
			byte[] sndData = MidConversion.getErrRespBytes(isLatestVersion, th);
 
			out.writeInt(sndData.length); // error data length
 
			out.writeBytes(sndData); // error data
 
			logResponseData(sndData);
		}
	}
 
	/**
	 * 托管核心
	 * 
	 * @param ctx
	 * @param msg
	 * @param out
	 */
	private void encode1(ChannelHandlerContext ctx, Response msg, ByteBuf out) {
		try {
			if (msg == null) {
				return;
			}
			// revised by 80374311 2017-02-21 系统优化，不进行底层数组的复制
			// Convert the Response into a byte array.
			// byte[] data = msg.getBytes();
			// int dataLength = data.length;
 
			// Write a message.
			// out.writeInt(dataLength); // data length
 
			// out.writeBytes(data); // data
 
			out.writeInt(msg.responseLength());
 
			msg.writeBytes(out);
 
			logResponseData(msg);
 
		} catch (Throwable th) {
			// Write error message.
			Response errResp = Response.createFailed(th);
 
			// byte[] errData = errResp.getBytes();
 
			// out.writeInt(errData.length); // error data length
 
			// out.writeBytes(errData); // error data
 
			out.writeInt(errResp.responseLength());
 
			errResp.writeBytes(out);
 
			logResponseData(errResp);
		}
	}
 
	private void logResponseData(Response req) {
		if (log.isDebugEnabled()) {
			logResponseData(req.getBytes());
		}
	}
 
	private void logResponseData(byte[] req) {
		if (log.isDebugEnabled()) {
			// 大的日志打印信息过滤掉
			if (req.length < Platform.REQ$RESP_DATA_LOG_MAX_LENGTH) {
				try {
					log.debug("response data:\n" + new String(req, "GBK"));
				} catch (UnsupportedEncodingException e) {
					// record and swallow it
					log.error(e);
				}
			} else {
				log.debug("response data is too large, system discard log output for good performance");
			}
 
		}
	}
}
