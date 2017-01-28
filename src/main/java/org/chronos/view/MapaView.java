package org.chronos.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.chronos.dao.MarcadorDao;
import org.chronos.model.MarcadorModel;
import org.primefaces.component.gmap.GMap;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;


@Model
public class MapaView {

	private MapModel mapModel;
	private String nomeMarcador;
	private double lat = -5.837658;
	private double lng =-35.213417;
	private int zoon = 12;
 

	@Inject
	private MarcadorDao marcadorDao;

	@PostConstruct
	public void init() {

		 
		setMapModel(new DefaultMapModel());
		updateMarcadores();

	}

	public MapModel getMapModel() {
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	public String getNomeMarcador() {
		return nomeMarcador;
	}

	public void setNomeMarcador(String nomeMarcador) {
		this.nomeMarcador = nomeMarcador;
	}

	

	 
 
	public void updateMarcadores() {
		
		 

		List<MarcadorModel> marcadorModels = marcadorDao.listAll();

		for (MarcadorModel model : marcadorModels) {

			Marker marker = new Marker(new LatLng(model.getLatitude(), model.getLongitude()), model.getLogradouro());
			marker.setDraggable(true);
			marker.setIcon("http://localhost:8080/chronos/resources/icones/" + model.getIcone());
			getMapModel().addOverlay(marker);

		}

	}

	public void publishMapa() {

		marcadorDao.envMessage();

	}
	
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getZoon() {
		return zoon;
	}

	public void setZoon(int zoon) {
		this.zoon = zoon;
	}

 
}
