package Enum;

public enum LogsEnum {

	CADASTROU("cadastrou"), ALTEROU("alterou"), DELETOU("deletou");

	private String nome;

	private LogsEnum(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {

		return this.nome;
	}
}
