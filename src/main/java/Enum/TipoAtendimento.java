package Enum;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoAtendimento {

	ESCOLA("Escola"), BOLSA_ESTUDOS("Bolsa de Estudos"), EMPREGA_MAIS("Emprega mais");
	
	private String nome;
	
	private TipoAtendimento (String nome) {
		
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		
		return this.nome;
	}
	
}