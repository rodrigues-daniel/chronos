package org.chronos.view;

import javax.enterprise.inject.Model;


@Model
public class AlimentadorOrdens {
	
	
	private String textoEntrada;
	
	

	public String getTextoEntrada() {
		return textoEntrada;
	}

	public void setTextoEntrada(String textoEntrada) {
		this.textoEntrada = textoEntrada;
	}
	
	
	
	public void salvarTexto() {
		System.out.println("Texto Salvo");
	}
	
	
	
	
}