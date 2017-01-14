package org.chronos.view;

import javax.enterprise.inject.Model;

import javax.inject.Inject;

@Model
public class MapaMB {

	@Inject
	@Mapeador
	private MapaInterface mapaInter;

	private String texto = "texto ok";

	public String getTexto() {

		mapaInter.envMenssagem();
		

		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
