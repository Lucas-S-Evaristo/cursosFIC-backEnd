package Enum;

public enum AcoesLinhaDoTempo {

	DATA_LIM_INSC("Data limite da inscrição"), VERI_PCDS("Verificar PDCS"), CONFIRM_TUR("Confirmar turma"),COBRAR_ENTREG_DOC("Cobrar entrega dos documentos"), GERAR_DIAR_ELETR("Gerar Diário Eletrônico"),MONTAR_KIT_TURMA("Montar Kit"),ENCERRAR_TURMA("Encerrar turma"),VER_QUEM_FALT("Verificar quem faltou no primeiro dia"),INICIAR_TURM("Iniciar Turma"),MATRCUL_DEFINITIV("Matrícula definitiva"),ESCANER_DOC("Escanear documentos"), RETIRADA_SITE("Retirar anúncio do site");

	private String nome;
	
	private AcoesLinhaDoTempo(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		
		return this.nome;
	}
}
