package org.chronos.ws;

import org.primefaces.json.JSONObject;
import org.primefaces.model.map.MapModel;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

@PushEndpoint("/mpsocket")
public class MapaSocket {

	@OnOpen
	public void onOpen() {

		System.out.println("open socket aberto");
	}

	@OnMessage(encoders = { JSONEncoder.class })
	public String onMessage(String menssagem) {			
		
		
		System.out.println("Menssagem Retornada pelo console: " + menssagem);

		return  menssagem;
	}

}