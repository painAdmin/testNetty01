package cn.yesway.demo.privateprotocol.codec;

import java.io.IOException;

import org.jboss.marshalling.ByteOutput;

import io.netty.buffer.ByteBuf;

public class ChannelBufferByteOutput implements ByteOutput {

	private final ByteBuf buffer;
	public ChannelBufferByteOutput(ByteBuf buffer){
		this.buffer=buffer;
	}
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
	public void flush() throws IOException {
		// TODO Auto-generated method stub
		
	}
	public void write(int b) throws IOException {
		buffer.writeByte(b);
		
	}
	public void write(byte[] bytes) throws IOException {
		buffer.writeBytes(bytes);
		
	}
	public void write(byte[] bytes, int srcIndex, int length) throws IOException {
		buffer.writeBytes(bytes, srcIndex, length);
		
	}
	ByteBuf getBuffer(){
		return buffer;
	}
	
}
