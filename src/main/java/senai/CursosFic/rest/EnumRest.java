package senai.CursosFic.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Enum.Nivel;
import Enum.Periodo;
import Enum.Status;
import Enum.TipoAtendimento;
import Enum.TipoUsuario;

@RestController
@RequestMapping("/api/enum")
public class EnumRest {

	// pegando todos os dados
	@GetMapping("/tipoAtendimento")
	public TipoAtendimento[] getTipo() {
		return TipoAtendimento.values();
	}

	// pegando todos os dados
	@GetMapping("/periodo")
	public Periodo[] getPeriodo() {
		return Periodo.values();
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
