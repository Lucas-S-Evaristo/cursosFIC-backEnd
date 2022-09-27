package Enum;

public enum TipoAtendimento {

	ESCOLA("Manhã"), BOLSA_ESTUDOS("Bolsa de Estudos"), EMPREGA_MAIS("Emprega mais");
	
	private String nome;
	
	private TipoAtendimento (String nome) {
		
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		
		return this.nome;
	}
}