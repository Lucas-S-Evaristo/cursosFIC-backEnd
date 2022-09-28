package Enum;

public enum Nivel {

	APERFEICOAMENTO("aperfeicoamento"), QUALIFICACAO("qualificacao"), ESPECIALIZACAO("especializacao"),
	INICIACAO("iniciacao");

	private String inicial;
	private String nome;

	private Nivel(String nome) {

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