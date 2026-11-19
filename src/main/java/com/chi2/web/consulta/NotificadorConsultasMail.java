package com.chi2.web.consulta;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/** Avisa por mail (Gmail SMTP en producción, Mailpit en local) a todos los destinatarios (§7). */
@Component
public class NotificadorConsultasMail implements NotificadorConsultas {

	private final JavaMailSender mailSender;
	private final MensajeAvisoConsulta mensaje;
	private final ConsultasProperties properties;

	public NotificadorConsultasMail(JavaMailSender mailSender, MensajeAvisoConsulta mensaje,
			ConsultasProperties properties) {
		this.mailSender = mailSender;
		this.mensaje = mensaje;
		this.properties = properties;
	}

	@Override
	public boolean estaConfigurado() {
		return properties.hayDestinatarios();
	}

	@Override
	public void avisar(Consulta consulta) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(properties.remitente());
		mail.setTo(properties.destinatarios().toArray(String[]::new));
		if (consulta.getTipoContacto() == TipoContacto.MAIL) {
			mail.setReplyTo(consulta.getContacto());
		}
		mail.setSubject(mensaje.asunto(consulta));
		mail.setText(mensaje.cuerpo(consulta));
		mailSender.send(mail);
	}

}
