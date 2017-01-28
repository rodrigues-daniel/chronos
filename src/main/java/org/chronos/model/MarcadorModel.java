package org.chronos.model;

import javax.persistence.Entity;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.persistence.Enumerated;
import org.chronos.model.Icone;

@Entity
public class MarcadorModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private String logradouro;

	@Column(length = 7)
	private String numero;

	@Column(length = 10)
	private double latitude;

	@Column(length = 10)
	private double longitude;

	@Column(length = 50)
	private String icone;

	@Enumerated
	private Icone funcao;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MarcadorModel)) {
			return false;
		}
		MarcadorModel other = (MarcadorModel) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public Icone getFuncao() {
		return funcao;
	}

	public void setFuncao(Icone funcao) {
		this.funcao = funcao;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		result += ", version: " + version;
		if (logradouro != null && !logradouro.trim().isEmpty())
			result += ", logradouro: " + logradouro;
		if (numero != null && !numero.trim().isEmpty())
			result += ", numero: " + numero;
		result += ", latitude: " + latitude;
		result += ", longitude: " + longitude;
		if (icone != null && !icone.trim().isEmpty())
			result += ", icone: " + icone;
		if (funcao != null)
			result += ", funcao: " + funcao;
		return result;
	}

}