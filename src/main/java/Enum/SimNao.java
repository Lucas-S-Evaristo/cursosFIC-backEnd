package Enum;


public enum SimNao {

	SIM("Sim"),NAO("Não");
	
private String nome;
	
	private SimNao (String nome) {
		
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		
		return this.nome;
	}

}
