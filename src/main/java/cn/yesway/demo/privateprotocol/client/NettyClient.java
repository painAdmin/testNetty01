package cn.yesway.demo.privateprotocol.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import cn.yesway.demo.privateprotocol.codec.NettyMessageDecoder;
import cn.yesway.demo.privateprotocol.codec.NettyMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;  
public class NettyClient {

	private ScheduledExecutorService executor=Executors.newScheduledThreadPool(1);
	public void connect(String remoteServer,int port)throws Exception{
		EventLoopGroup workGroup=new NioEventLoopGroup();
		try{
			Bootstrap b=new Bootstrap();
			b.group(workGroup).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChildChannelHandler());
			
			ChannelFuture f=b.connect(remoteServer, port).sync();
			System.out.println("Netty time Client connected at port :"+port);
			f.channel().closeFuture().sync();
		}finally{
			workGroup.shutdownGracefully();
		}
	}
	public static class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			//-8表示lengthAdjustment，让解码器从0开始截取字节,并且包含消息头
			ch.pipeline().addLast(new NettyMessageDecoder(1024*1024, 4, 4))
			.addLast("MessageEncoder",new NettyMessageEncoder())
			.addLast("LoginAuthReqHandler",new LoginAuthReqHandler());
		}
		
	}
	public static void main(String[] args) throws Exception{
		int port=8078;
		String host="127.0.0.1";
		new NettyClient().connect(host, port);
	}
	
}
