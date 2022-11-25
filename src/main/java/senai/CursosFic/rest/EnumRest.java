package senai.CursosFic.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Enum.DiaSemana;
import Enum.Nivel;
import Enum.Periodo;
import Enum.SimNao;
import Enum.Status;
import Enum.TipoAtendimento;
import Enum.TipoUsuario;
import lombok.Data;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/enum")
public class EnumRest {
	
	


	// pegando todos os dados
	@GetMapping("/tipoAtendimento")
	public String[] getTipo() {
		int length = TipoAtendimento.values().length;
		String[] retorno = new String[length];
		int i = 0;
		for(TipoAtendimento tipo : TipoAtendimento.values()) {
			retorno[i] = tipo.toString();			
			i++;
		}			
		return retorno;
		//return TipoAtendimento.values();
	}
	
	// pegando todos os dados
		@GetMapping("/simEnao")
		public String[] getSimNao() {
			int length = SimNao.values().length;
			String[] retorno = new String[length];
			int i = 0;
			for(TipoAtendimento tipo : TipoAtendimento.values()) {
				retorno[i] = tipo.toString();			
				i++;
			}			
			return retorno;
		}

	// pegando todos os dados
	@GetMapping("/periodo")
	public Periodo[] getPeriodo() {
		return Periodo.values();
	}

	// pegando todos os dados
	@GetMapping("/diasSemana")
	public DiaSemana[] getDiasSemana() {
		return DiaSemana.values();
	}

	// pegando todos os dados
	@GetMapping("/nivel")
	public Nivel[] getNivel() {
		return Nivel.values();
	}

	// pegando todos os dados
	@GetMapping("/status")
	public Status[] getStatus() {
		return Status.values();
	}

	// pegando todos os dados
	@GetMapping("/tipoUsuario")
	public TipoUsuario[] getTipoUsuario() {
		return TipoUsuario.values();
	}

}
