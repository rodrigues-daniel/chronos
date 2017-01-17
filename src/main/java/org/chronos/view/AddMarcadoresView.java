package org.chronos.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;

import javax.inject.Inject;

import org.chronos.dao.MarcadorDao;
import org.chronos.model.MarcadorModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Model
public class AddMarcadoresView {

	private MapModel mapModel;
	private String nomeMarcador;
	private double lat;
	private double lon;

	@Inject
	private MarcadorDao marcadorDao;

	@PostConstruct
	public void init() {

		setMapModel(new DefaultMapModel());
		addMarcadores();

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

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	private void addMarcadores() {

		List<MarcadorModel> marcadorModels = marcadorDao.listAll();

		for (MarcadorModel model : marcadorModels) {

			Marker marker = new Marker(new LatLng(model.getLatitude(), model.getLongitude()), model.getLogradouro());
			getMapModel().addOverlay(marker);
		}

	}



}
