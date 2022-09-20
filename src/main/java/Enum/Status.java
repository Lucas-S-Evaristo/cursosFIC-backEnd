package Enum;

public enum Status {

	ABERTO("Aberto"),FECHADA("Fechada"),CANCELADA("Cancelada");
	
	private String nome;
	
	private Status(String nome) {
		
		this.nome = nome;
	}
	
	@Override
	public String toString() {

		return this.nome;
	}
}