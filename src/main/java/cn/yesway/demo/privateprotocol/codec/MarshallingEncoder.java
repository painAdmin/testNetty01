package cn.yesway.demo.privateprotocol.codec;

import org.jboss.marshalling.Marshaller;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class MarshallingEncoder {

	private static final byte[] LENGTH_PLACEHOLDER=new byte[4];
	Marshaller marshaller;
	public MarshallingEncoder() throws Exception{
		marshaller=MarshallingCodeCFactory.buildMarshalling();
	}
	//使用marshall对Object 进行编码，并写入到bytebuf中
	protected void encode(Object msg,ByteBuf out) throws Exception{
		try{
			//1 获取写入位置
			int lengthPos=out.writerIndex();
			//2 先写入 4个byte，用于记录object对象编码长度
			out.writeBytes(LENGTH_PLACEHOLDER);
			//3 使用代理对象 防止写入完marshaller 之后关闭byte buf
			ChannelBufferByteOutput output=new ChannelBufferByteOutput(out);
			//4 开始使用marshaller 往bytebuf中编码
			marshaller.start(output);
			marshaller.writeObject(msg);
			//5 结束编码
			marshaller.finish();
			// 6设置对象长度
			out.setInt(lengthPos, out.writerIndex()-lengthPos-4);
		}finally{
			marshaller.close();
		}
	}
}
