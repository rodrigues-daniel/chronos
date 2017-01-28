package org.chronos.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import org.chronos.dao.MarcadorDao;
import org.chronos.model.MarcadorModel;
import org.primefaces.component.gmap.GMap;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@Model
public class MapaView {

	private MapModel mapModel;
	private String nomeMarcador;
	private double lat = 10;
	private double lon = 1000;

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

	public void updateMarcadores() {

		List<MarcadorModel> marcadorModels = marcadorDao.listAll();

		for (MarcadorModel model : marcadorModels) {

			Marker marker = new Marker(new LatLng(model.getLatitude(), model.getLongitude()), model.getLogradouro());
			getMapModel().addOverlay(marker);

		}

	}

	public void publishMapa() {

		marcadorDao.envMessage();

	}

}
