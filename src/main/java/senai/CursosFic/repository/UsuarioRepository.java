package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import Enum.TipoUsuario;
import senai.CursosFic.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

	public List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);
}
