package br.com.fiap.cgenius.mail;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    public void sendEmailScript(String mensagem) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("Marcos@gmail.com");
        mail.setSubject("Script");
        mail.setText(mensagem);
        mailSender.send(mail);
    }
    @RabbitListener(queues = "chat-queue")
    public void sendEmail(String mensagem) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("Marcos@gmail.com");
        mail.setSubject("Cadastro efetuado");
        mail.setText(mensagem);
        mailSender.send(mail);
    }
}
