package notifier;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email implements Observer,Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dateInEmail;
    private Observable aSubject;
    boolean checkNotification;
	
    Email(){
    	checkNotification = false;
    }
    
    Date getdateInEmail(){
    	 return dateInEmail;
    }
    
    boolean checkNotification(){
    	return checkNotification;
    }

    //updates the state of observer and send a mail regarding the updated date
	public void update(Observable subject,Object msg){
		aSubject= subject;
		dateInEmail = ((WebpageUpdateNotifier) subject).getCurrentModifiedDate();
		System.out.println(msg);
		checkNotification = true;
		sendEmail();
	}
		
	void sendEmail(){	
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("g.likhita@gmail.com","sriramlu");
				}
			});
 
		try { 
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("g.likhita@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("g.likhita@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Likhita," +
					"\n\n The webpage has been updated on " + dateInEmail.toString());
 
			Transport.send(message);
 
			System.out.println("Email sent !");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		}
	}



