package Enum;

public enum Periodo {
	MANHA("Manhã"), TARDE("Tarde"), NOITE("Noite"), INTEGRAL("Integral");

	private String inicial;
	private String nome;

	private Periodo(String nome) {
		this.nome = nome;
		inicial = String.valueOf(nome.charAt(0));
	}

	public String getInicial() {
		return this.inicial;
	}

	@Override
	public String toString() {

		return this.nome;
	}
}