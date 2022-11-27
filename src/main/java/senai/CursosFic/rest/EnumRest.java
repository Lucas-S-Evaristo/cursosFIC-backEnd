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
	}
	
	// pegando todos os dados
		@GetMapping("/simEnao")
		public String[] getSimNao() {
			int length = SimNao.values().length;
			String[] retorno = new String[length];
			int i = 0;
			for(SimNao tipo : SimNao.values()) {
				retorno[i] = tipo.toString();			
				i++;
			}			
			return retorno;
		}

	// pegando todos os dados
	@GetMapping("/periodo")
	public String[] getPeriodo() {
		int length = Periodo.values().length;
		String[] retorno = new String[length];
		int i = 0;
		for(Periodo tipo : Periodo.values()) {
			retorno[i] = tipo.toString();			
			i++;
		}			
		return retorno;
	}

	// pegando todos os dados
	@GetMapping("/nivel")
	public String[] getNivel() {
		int length = Nivel.values().length;
		String[] retorno = new String[length];
		int i = 0;
		for(Nivel tipo : Nivel.values()) {
			retorno[i] = tipo.toString();			
			i++;
		}			
		return retorno;
	}

	// pegando todos os dados
	@GetMapping("/status")
	public String[] getStatus() {
		int length = Status.values().length;
		String[] retorno = new String[length];
		int i = 0;
		for(Status tipo : Status.values()) {
			retorno[i] = tipo.toString();			
			i++;
		}			
		return retorno;
	}

	// pegando todos os dados
	@GetMapping("/tipoUsuario")
	public String[] getTipoUsuario() {
		int length = TipoUsuario.values().length;
		String[] retorno = new String[length];
		int i = 0;
		for(TipoUsuario tipo : TipoUsuario.values()) {
			retorno[i] = tipo.toString();			
			i++;
		}			
		return retorno;
	}

}
