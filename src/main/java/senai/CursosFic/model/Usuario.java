package senai.CursosFic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.jasypt.util.text.BasicTextEncryptor;

import Enum.TipoUsuario;
import lombok.Data;

@Entity
@Data
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String nome;
	@Column(unique = true)
	@NotNull
	private String email;
	@Column(unique = true)
	@NotNull
	private String nif;

	private String senha;

	private boolean redefinirSenha;

	private TipoUsuario tipoUsuario;
	
	// metodo para setar a senha aplicando o hash
		public void setSenha(String senha) {

			// aplica o hash e seta a senha no objeto
			this.senha = HashUtil.hash256(senha);
		}

		public void setSenhaSemHash(String hash) {

			// tira o hash na senha
			this.senha = hash;

		}

	// metodo que criptografia o nif do usuario, utilizando o proprio setNif
	public void setNif(String nif) {

		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

		textEncryptor.setPassword("opensezame");

		String nifCrip = textEncryptor.encrypt(nif);

		this.nif = nifCrip;

	}

	// metodo que descriptografia o nif do usuario, utilizando o proprio getNif
	public String getNif() {

		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

		textEncryptor.setPassword("opensezame");

		String nifDesc = textEncryptor.decrypt(this.nif);

		return nifDesc;

	}

	// metodo que criptografia o email do usuario, utilizando o proprio setEmail
	public void setEmail(String email) {

		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

		textEncryptor.setPassword("opensezame");

		String emailCrip = textEncryptor.encrypt(email);

		this.email = emailCrip;

	}

	// metodo que descriptografia o email do usuario, utilizando o proprio getEmail

	public String getEmail() {

		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

		textEncryptor.setPassword("opensezame");

		String emailDesc = textEncryptor.decrypt(this.email);

		return emailDesc;

	}

}
