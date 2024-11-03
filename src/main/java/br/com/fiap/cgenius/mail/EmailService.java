// package br.com.fiap.cgenius.mail;

// import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.MailSender;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;

// @Service
// public class EmailService {

//     @Autowired
//     JavaMailSender mailSender;

//     @RabbitListener(queues = "email-queue")
//     public void sendEmail(String mensagem, String email) {
//         SimpleMailMessage mail = new SimpleMailMessage();
//         mail.setTo(email);
//         mail.setSubject("Cadastro efetuado");
//         mail.setText(mensagem);
//         mailSender.send(mail);
//     }
// }
