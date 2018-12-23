package netty;
 
import baselib.CmbPacket;
import frame.Request;
import frame.Response;
 
/**
 * 保存channel pipe过程中的数据
 * 
 * @author 80374311 <br>
 */
public class ChannelBindData {
 
	private ChannelBindData() {
	}
 
	// 数据类型
	private BindDataType dataType = BindDataType.REQUEST;
 
	/** 托管银行请求和返回数据 */
	private boolean isLatestVersion;
	private CmbPacket reqPacket;
	private byte[] respBytes;
 
	/** 托管核心请求和返回数据 */
	private Request request;
	private Response response;
 
	public BindDataType getDataType() {
		return dataType;
	}
 
	public void setDataType(BindDataType dataType) {
		this.dataType = dataType;
	}
 
	public Request getRequest() {
		return request;
	}
 
	public void setRequest(Request request) {
		this.request = request;
	}
 
	public Response getResponse() {
		return response;
	}
 
	public void setResponse(Response response) {
		this.response = response;
	}
 
	public CmbPacket getReqPacket() {
		return reqPacket;
	}
 
	public void setReqPacket(CmbPacket reqPacket) {
		this.reqPacket = reqPacket;
	}
 
	public boolean isLatestVersion() {
		return isLatestVersion;
	}
 
	public void setLatestVersion(boolean isLatestVersion) {
		this.isLatestVersion = isLatestVersion;
	}
 
	public byte[] getRespBytes() {
		return respBytes;
	}
 
	public void setRespBytes(byte[] respBytes) {
		this.respBytes = respBytes;
	}
 
	/**
	 * 数据类型<br>
	 * REQUEST - 核心请求数据<br>
	 * RESPONSE - 核心返回数据<br>
	 * REQUEST_PACKET - 托管银行请求数据<br>
	 * RESPONSE_PACKET -托管银行返回数据<br>
	 */
	private static enum BindDataType {
		REQUEST(1), RESPONSE(2), REQUEST_PACKET(3), RESPONSE_PACKET(4);
 
		private int dataType;
 
		BindDataType(int dataType) {
			this.dataType = dataType;
		}
 
		public String toString() {
			return dataType + "";
		}
	}
 
	/**
	 * 核心请求数据
	 * 
	 * @param request
	 * @return
	 */
	public static ChannelBindData createRequst(Request request) {
		ChannelBindData d = new ChannelBindData();
		d.dataType = BindDataType.REQUEST;
		d.request = request;
		return d;
	}
 
	/**
	 * 核心返回数据
	 * 
	 * @param response
	 * @return
	 */
	public static ChannelBindData createResponse(Response response) {
		ChannelBindData d = new ChannelBindData();
		d.dataType = BindDataType.RESPONSE;
		d.response = response;
		return d;
	}
 
	/**
	 * 托管银行请求数据
	 * 
	 * @param reqPacket
	 * @param isLatestVersion
	 * @return
	 */
	public static ChannelBindData createRequestPacket(CmbPacket reqPacket,
			boolean isLatestVersion) {
		ChannelBindData d = new ChannelBindData();
		d.dataType = BindDataType.REQUEST_PACKET;
		d.reqPacket = reqPacket;
		d.isLatestVersion = isLatestVersion;
		return d;
	}
 
	/**
	 * 托管银行返回数据
	 * 
	 * @param respBytes
	 * @param isLatestVersion
	 * @return
	 */
	public static ChannelBindData createResponsePacket(byte[] respBytes,
			boolean isLatestVersion) {
		ChannelBindData d = new ChannelBindData();
		d.dataType = BindDataType.RESPONSE_PACKET;
		d.respBytes = respBytes;
		d.isLatestVersion = isLatestVersion;
		return d;
	}
 
	public boolean isRequest() {
		return this.dataType == BindDataType.REQUEST;
	}
 
	public boolean isResponse() {
		return this.dataType == BindDataType.RESPONSE;
	}
 
	public boolean isRequestPacket() {
		return this.dataType == BindDataType.REQUEST_PACKET;
	}
 
	public boolean isResponsePacket() {
		return this.dataType == BindDataType.RESPONSE_PACKET;
	}
}
