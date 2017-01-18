package org.chronos.ws;


import javax.inject.Inject;

import org.chronos.view.AddMarcadoresView;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

@PushEndpoint("/mpsocket")
public class MapaSocket {
	 
	
	@Inject
	private AddMarcadoresView mview;
	

	@OnOpen
	public void onOpen() {

		System.out.println("open socket aberto");
	}

	@OnMessage(encoders = { JSONEncoder.class })
	public String onMessage(String menssagem) {		 
			
		System.out.println("Menssagem Retornada pelo console: " + menssagem);
		
		mview.addMarcadores();

		return menssagem ;
	}
 



}