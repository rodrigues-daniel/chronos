package org.chronos.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.persistence.Enumerated;
import org.chronos.model.Servico;
import org.chronos.model.Motivo;
import org.chronos.model.Cidade;

@Entity
public class EntradaDeOrdem implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private String ordem;

	@Column
	private String endereco;

	@Column
	private String assinante;

	@Enumerated
	private Servico servico;

	@Enumerated
	private Motivo motivo;

	@Enumerated
	private Cidade cidade;

	@Column
	private String nodo;

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
		if (!(obj instanceof EntradaDeOrdem)) {
			return false;
		}
		EntradaDeOrdem other = (EntradaDeOrdem) obj;
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

	public String getOrdem() {
		return ordem;
	}

	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getAssinante() {
		return assinante;
	}

	public void setAssinante(String assinante) {
		this.assinante = assinante;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getNodo() {
		return nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		result += ", version: " + version;
		if (ordem != null && !ordem.trim().isEmpty())
			result += ", ordem: " + ordem;
		if (endereco != null && !endereco.trim().isEmpty())
			result += ", endereco: " + endereco;
		if (assinante != null && !assinante.trim().isEmpty())
			result += ", assinante: " + assinante;
		if (servico != null)
			result += ", servico: " + servico;
		if (motivo != null)
			result += ", motivo: " + motivo;
		if (cidade != null)
			result += ", cidade: " + cidade;

		if (nodo != null && !nodo.trim().isEmpty())
			result += ", nodo: " + nodo;
		return result;
	}
}