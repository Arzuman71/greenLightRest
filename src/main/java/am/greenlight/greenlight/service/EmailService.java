package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Locale;

@Service

public class EmailService {

    @Autowired
    private MailSender mailSender;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;


    @Async
    public void send(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);


    }

    @Async
    public void sendHtmlEmil(String to, String subject, User user, String link, String templateName, Locale locale) throws MessagingException, MessagingException {
        final Context ctx = new Context(locale);
        ctx.setVariable("user", user);
        ctx.setVariable("url", link);
        //    ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
        //    ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

        final String htmlContent = this.templateEngine.process(templateName, ctx);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject(subject);
        message.setFrom("thymeleaf@example.com");
        message.setTo(to);

        message.setText(htmlContent, true); // true = isHtml

        // Send mail
        this.javaMailSender.send(mimeMessage);
    }
}
