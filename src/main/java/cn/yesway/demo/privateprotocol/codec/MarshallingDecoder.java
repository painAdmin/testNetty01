package cn.yesway.demo.privateprotocol.codec;

import java.io.IOException;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import io.netty.buffer.ByteBuf;

public class MarshallingDecoder {

	private final Unmarshaller unmarshaller;
	public MarshallingDecoder() throws IOException{
		unmarshaller=MarshallingCodeCFactory.buildUnmarshlling();
	}
	protected Object decode(ByteBuf in) throws IOException, ClassNotFoundException{
		//1 读取第一个4 bytes， 里面放置的是object 的长度
		int objectSize=in.readerIndex();
		ByteBuf buf=in.slice(in.readerIndex(), objectSize);
		//2 使用bytebuf 代理类
		ByteInput input=new ChannelBufferByteInput(buf);
		try{
			//3 开始解码
			unmarshaller.start(input);
			Object obj=unmarshaller.readObject();
			unmarshaller.finish();
			//4 读取之后设置读取位置
			in.readerIndex(in.readerIndex()+objectSize);
			return obj;
		}finally{
			unmarshaller.close();
		}
		
	}
}
