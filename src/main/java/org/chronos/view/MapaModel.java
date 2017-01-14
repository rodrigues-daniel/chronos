package org.chronos.view;

import org.chronos.dao.GeoposicaoDao;
import org.chronos.model.Geoposicao;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Overlay;
import org.primefaces.model.map.Polygon;
import org.primefaces.model.map.Polyline;
import org.primefaces.model.map.Rectangle;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.decorator.Decorator;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Produces;
 


@Decorator @Priority(Interceptor.Priority.APPLICATION)
public class MapaModel implements MapModel {

	@Inject
	@Delegate @Mapeador
	private MapModel delegate;

	/*
	@Inject
	private GeoposicaoDao geoPos;

	private List<Geoposicao> mapModel;
	private List<Marker> markers;

	@PostConstruct
	private void initMarcadores() {

		mapModel = geoPos.listAll();

	}*/

	@Override
	public void addOverlay(Overlay overlay) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Marker> getMarkers() {
		/*Marker marker = new Marker(new LatLng(0, 0));

		for (Geoposicao geoposicao : mapModel) {

			marker.setLatlng(new LatLng(geoposicao.getLatitude(), geoposicao.getLongitude()));

			markers.add(marker);

		}

		return delegate.getMarkers();*/
		
		return null;
	}

	@Override
	public List<Polyline> getPolylines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Polygon> getPolygons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Circle> getCircles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rectangle> getRectangles() {
		// TODO Auto-generated method stub
		return null;
	}
	
	  

	@Override
	public Overlay findOverlay(String id) {
		// TODO Auto-generated method stub		
		
		 
		return null;
	}

}