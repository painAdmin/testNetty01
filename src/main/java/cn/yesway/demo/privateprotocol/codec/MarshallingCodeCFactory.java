package cn.yesway.demo.privateprotocol.codec;


import java.io.IOException;

import org.jboss.marshalling.*;


public final class MarshallingCodeCFactory {

	public static Marshaller buildMarshalling() throws IOException{
		MarshallerFactory marshallerFactory=Marshalling.getProvidedMarshallerFactory("serial");
	    MarshallingConfiguration configuration=new MarshallingConfiguration();
		configuration.setVersion(5);
		Marshaller marshaller=marshallerFactory.createMarshaller(configuration);
		return marshaller;
	}
	
	public static Unmarshaller buildUnmarshlling() throws IOException{
		MarshallerFactory marshallerFactory=Marshalling.getProvidedMarshallerFactory("serial");
		MarshallingConfiguration configuration=new MarshallingConfiguration();
		configuration.setVersion(5);
		Unmarshaller unmarshaller=marshallerFactory.createUnmarshaller(configuration);
		return unmarshaller;
	}
}
