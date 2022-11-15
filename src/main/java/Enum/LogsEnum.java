package Enum;

public enum LogsEnum {

	CADASTROU("Cadastrou"), ALTEROU("Alterou"), DELETOU("Deletou");

	private String nome;

	private LogsEnum(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {

		return this.nome;
	}
}
