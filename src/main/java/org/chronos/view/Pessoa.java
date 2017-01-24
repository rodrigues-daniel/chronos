package org.chronos.view;

public class Pessoa {
	
	



	private String idade;
	private String pessoa;
	
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIdade() {
		return idade;
	}
	public void setIdade(String idade) {
		this.idade = idade;
	}


	public String getPessoa() {
		return pessoa;
	}
	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}

	
	
	public void exbiMenssagem(){
		
		this.pessoa = " " +  this.nome + " " +  this.idade;
		
		
	}
	
	private String nome;
	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + ", idade=" + idade + ", pessoa=" + pessoa + "]";
	}

	
	
}
