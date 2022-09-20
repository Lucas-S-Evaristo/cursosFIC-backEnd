package Enum;

public enum TipoUsuario {
	
	OPP("Opp"),MASTER("Master"), SECRETARIA("Secretária");
	
	private String nome;
	
	private TipoUsuario(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		
		return this.nome;
	}

}