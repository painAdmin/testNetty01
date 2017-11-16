package cn.yesway.demo.privateprotocol.codec;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.yesway.demo.privateprotocol.model.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;



public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage>{

    MarshallingEncoder  marshallingEncoder;
	public NettyMessageEncoder() throws Exception{
		 marshallingEncoder =new MarshallingEncoder();
	}
	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMessage msg,  ByteBuf sendBuf) throws Exception {
		if(msg==null || msg.getHeader() ==null){
			throw new Exception("The encode message is null");
		}
		sendBuf.writeInt((msg.getHeader()).getCrcCode());
		sendBuf.writeInt((msg.getHeader()).getLength());
		sendBuf.writeLong((msg.getHeader()).getSessoinID());
		sendBuf.writeByte((msg.getHeader()).getType());
		sendBuf.writeByte((msg.getHeader()).getPriority());
		sendBuf.writeInt((msg.getHeader()).getAttachment().size());
		
		String key=null;
		byte[] keyArray=null;
		Object value=null;
		for(Map.Entry<String,Object> param:msg.getHeader().getAttachment().entrySet()){
			key=param.getKey();
			keyArray=key.getBytes("UTF-8");
			sendBuf.writeInt(keyArray.length);
			sendBuf.writeBytes(keyArray);
			value=param.getValue();
			//marshallingEncoder.encode(ctx,value, sendBuf);
		}
		key=null;
		keyArray=null;
		value=null;
		if(msg.getBody()!=null){ 
			marshallingEncoder.encode(msg.getBody(),sendBuf);
		}else
			sendBuf.writeInt(0);
	    //之前写了 crcCode 4bytes，除去crcCode和length 8bytes即为之更新之后的字节
		sendBuf.setInt(4, sendBuf.readableBytes()-8);

	}
}


















