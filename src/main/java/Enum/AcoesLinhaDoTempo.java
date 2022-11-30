package Enum;

public enum AcoesLinhaDoTempo {

	DATA_LIM_INSC("Data limite da inscrição"),RETIRADA_SITE("Retirar anúncio do site"),COBRAR_ENTREG_DOC("Cobrar entrega dos documentos"),VERI_PCDS("Verificar PDCS"),GERAR_DIAR_ELETR("Gerar Diário Eletrônico"),MONTAR_KIT_TURMA("Montar Kit"),INICIAR_TURM("Iniciar Turma"),MATRCUL_DEFINITIV("Matrícula definitiva"),CONFIRM_TUR("Confirmar turma"),ESCANER_DOC("Escanear documentos"),VER_QUEM_FALT("Verificar quem faltou no primeiro dia"),ENCERRAR_TURMA("Encerrar turma");

	private String nome;
	
	private AcoesLinhaDoTempo(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		
		return this.nome;
	}
}
