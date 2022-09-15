package senai.CursosFic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.jasypt.util.text.BasicTextEncryptor;

import Enum.TipoUsuario;
import lombok.Data;

@Entity
@Data
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String nif;
	private String senha;	
	private TipoUsuario tipoUsuario;

	// metodo para setar a senha aplicando o hash
	public void setSenhaHash(String senha) {

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
		textEncryptor.setPasswordCharArray("opensezame".toCharArray());

		String nifCrip = textEncryptor.encrypt(nif);
		System.out.println("crip " + nifCrip);

		this.nif = nifCrip;
	}

	// metodo que descriptografia o nif do usuario, utilizando o proprio getNif
	public String getNif(String nif) {

		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPasswordCharArray("opensezame".toCharArray());

		String nifDesc = textEncryptor.decrypt(nif);

		return nifDesc;

	}

}
