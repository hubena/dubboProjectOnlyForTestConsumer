package netty;
 
import java.util.concurrent.ExecutorService;
 
import core.log.ILogger;
import core.log.LogUtils;
import core.netty.channel.ChannelHandlerContext;
import core.netty.channel.ChannelInitializer;
import core.netty.channel.ChannelPipeline;
import core.netty.channel.socket.SocketChannel;
import core.netty.handler.codec.LengthFieldBasedFrameDecoder;
import core.netty.handler.logging.LoggingHandler;
 
/**
 * registered channel handlers chains to NIOEventLoop
 * 
 * @author 80374311
 */
public class ChannelRegister extends ChannelInitializer<SocketChannel> {
 
	private static ILogger log = LogUtils.getLogger(ChannelRegister.class);
 
	private ExecutorService executor;
 
	private int maxFrameLength;
 
	public ChannelRegister(ExecutorService executor, int maxFrameLength) {
		this.executor = executor;
		this.maxFrameLength = maxFrameLength;
	}
 
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new LoggingHandler());
		p.addLast(new LengthFieldBasedFrameDecoder(maxFrameLength, 0, 4, 0, 4));
		p.addLast(new ProtocolDecoder());
		p.addLast(new ProtocolEncoder());
		p.addLast(new BusinessExecution(executor));
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		log.error("Failed to initialize a channel. Closing: " + ctx.channel(),
				cause);
		try {
			ChannelPipeline pipeline = ctx.pipeline();
			if (pipeline.context(this) != null) {
				pipeline.remove(this);
			}
		} finally {
			ctx.close();
		}
	}
 
}
