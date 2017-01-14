package org.chronos.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.chronos.dao.GeoposicaoDao;
import org.chronos.model.Geoposicao;

@Mapeador
public class ExemploCDI implements MapaInterface {

	@Inject
	private GeoposicaoDao geoDao;
	
	private List<Geoposicao> geoMapa;
	
	
	@Override
	public void envMenssagem() {
		System.out.println("a menssagem foi chamada");

	}
	
	@PostConstruct
	private void initMapa(){
		
		geoMapa = geoDao.listAll();
		
		
		
	}
	
	
	
	
	

}
