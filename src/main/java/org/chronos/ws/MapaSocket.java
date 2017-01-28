package org.chronos.ws;



import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;
import org.view.MarcadorModelBean;

@PushEndpoint("/mpsocket")
public class MapaSocket {

 

	@OnOpen
	public void onOpen() {

		System.out.println("open socket aberto");

		

	}

	@OnMessage(encoders = { JSONEncoder.class })
	public String onMessage(String menssagem) {

		System.out.println("Menssagem Retornada pelo console: " + menssagem);

		return menssagem;
	}
	
	 

}