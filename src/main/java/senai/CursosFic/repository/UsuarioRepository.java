package senai.CursosFic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import Enum.TipoUsuario;
import senai.CursosFic.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

	public List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);

	@Query("SELECT u FROM Usuario u ORDER BY u.id desc")
	public List<Usuario> findAll();

	@Query("SELECT t FROM Usuario t WHERE t.nome LIKE %:p% OR t.tipoUsuario LIKE %:p% ORDER BY t.nome ASC")
	public List<Usuario> buscarUsuario(@Param("p") String parametro);

	@Query("SELECT t FROM Usuario t WHERE t.nif = :nif AND t.senha = :senha")
	public Usuario NIfESenha(@Param("nif") String nif, @Param("senha") String senha);

	public Usuario findByNifAndSenha(String nif, String senha);

	public Usuario findBySenha(String senha);

	public Usuario findByNif(String nif);

	@Query("SELECT t FROM Usuario t WHERE t.nif LIKE %:p%")
	public List<Usuario> buscarNif(@Param("p") String nif);

	@Query("SELECT t FROM Usuario t WHERE t.tipoUsuario = 1")
	public List<Usuario> qtdMaster();

}
