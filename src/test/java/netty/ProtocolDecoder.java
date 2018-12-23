package netty;
 
import java.io.UnsupportedEncodingException;
import java.util.List;
 
import server.MidConversion;
import server.Platform;
import baselib.CmbPacket;
import core.log.ILogger;
import core.log.LogUtils;
import core.netty.buffer.ByteBuf;
import core.netty.channel.ChannelHandlerContext;
import core.netty.handler.codec.MessageToMessageDecoder;
import frame.Request;
import frame.Response;
 
/**
 * @author 80374311 业务解码
 */
public class ProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
 
	private static ILogger log = LogUtils.getLogger(ProtocolDecoder.class);
 
	public ProtocolDecoder() {
	}
 
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) {
		if (!Platform.isccsBsrNo()) {
			decode0(ctx, msg, out);
		} else {
			decode1(ctx, msg, out);
		}
	}
 
	/**
	 * 托管银行
	 * 
	 * @param ctx
	 * @param msg
	 * @param out
	 */
	private void decode0(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) {
		boolean boolLatestVersion = true; // default
		try {
			byte[] rcvData = new byte[msg.readableBytes()];
			msg.readBytes(rcvData);
			logRequestData(rcvData);
			boolLatestVersion = isLatestVersion(rcvData);
			if (!boolLatestVersion) {
				rcvData = MidConversion.ConvertRequest(rcvData);
			}
			CmbPacket req = new CmbPacket(null);
			req.check(rcvData);
			out.add(ChannelBindData.createRequestPacket(req, boolLatestVersion));
 
		} catch (Throwable th) {
			// 解码失败
			log.error("Protocol decode error.", th);
			out.add(ChannelBindData.createResponsePacket(
					MidConversion.getErrRespBytes(boolLatestVersion, th),
					boolLatestVersion));
		}
	}
 
	/**
	 * 托管核心
	 * 
	 * @param ctx
	 * @param msg
	 * @param out
	 */
	private void decode1(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) {
		try {
			byte[] bytes = new byte[msg.readableBytes()];
			msg.readBytes(bytes);
			out.add(ChannelBindData.createRequst(new Request(bytes)));
			logRequestData(bytes);
		} catch (Throwable e) {
			// 解码失败
			log.error("Protocol decode error.", e);
			out.add(ChannelBindData.createResponse(Response.createFailed(e)));
		}
	}
 
	private void logRequestData(byte[] req) {
		if (log.isDebugEnabled()) {
			// 大的日志打印信息过滤掉
			if (req.length < Platform.REQ$RESP_DATA_LOG_MAX_LENGTH) {
				try {
					log.debug("request data:\n" + new String(req, "GBK"));
				} catch (UnsupportedEncodingException e) {
					// record and swallow it
					log.error(e);
				}
			} else {
				log.debug("request data is too large,"
						+ "system discard log output for good performance");
			}
		}
	}
 
	/**
	 * 托管银行检查协议版本
	 * 
	 * @param rcvData
	 * @return
	 * @throws Exception
	 */
	private static boolean isLatestVersion(byte[] rcvData) throws Exception {
		String m_version = checkVersion(rcvData);
		return (m_version != null) && m_version.equals("v1.2");
	}
 
	private static String checkVersion(byte[] rcvData) throws Exception {
		String m_version = "";
		if ((rcvData == null) || (rcvData.length < 2)) {
			throw new Exception("CmbPacket: check-offset/len overflow");
		}
 
		String tag = new String(rcvData, 0, 2);
		if (tag.equals("DL") || tag.equals("VL")) {
			m_version = "v1.2";
		} else {
			m_version = "v1.0";
		}
		return m_version;
	}
}
