package senai.CursosFic.Email;


import java.util.Properties;



import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

@Service
public class JavaEmailDaSenha {
	
	 public static void mandarEmail(String email, String senha) {



	       Properties props = new Properties();
	        /** Parâmetros de conexão com servidor Yahoo */
	        props.put("mail.smtp.host", "smtp.mail.yahoo.com");
	        // props.put("mail.smtp.socketFactory.port", "465");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.ssl.protocols=TLSv1.2", "true");
	        props.put("mail.smtp.ssl.trust", "*");



	       Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {



	               return new PasswordAuthentication("joao.silva1764321@yahoo.com", "wxjvsytyjezrwomj");
	            }
	        });



	       System.out.println("PASSEI AQUI");
	        session.getProperties().put("mail.smtp.starttls.enable", "true");
	        /** Ativa Debug para sessão */
	        session.setDebug(true);



	       try {
	            System.out.println("ENTREI NO TRY");
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress("joao.silva1764321@yahoo.com")); // Remetente
	            System.out.println("AQUIII");
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // Destinatário(s)
	            message.setSubject("SUA NOVA SENHA");// Assunto
	            message.setText(senha);



	           /** Método para enviar a mensagem criada */
	            System.out.println("TRANSPORTE");
	            Transport.send(message);



	           System.out.println("Feito!!!");



	       } catch (MessagingException e) {
	            System.out.println("ENTREI NO CATCH");
	            throw new RuntimeException(e);



	       }
	    }
}