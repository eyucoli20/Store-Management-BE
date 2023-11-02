package StoreManagement.notificationManager;

import io.micrometer.common.lang.NonNull;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Implementation of the EmailService interface for sending emails.
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void send(@NonNull String recipientEmail, @NonNull String emailBody, @NonNull String emailSubject) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(emailBody, true);
            helper.setTo(recipientEmail);
            helper.setSubject(emailSubject);
            this.mailSender.send(mimeMessage);
        } catch (MailException | MessagingException ex) {
            log.error("An error occurred in {}.{} while sending email. Details: {}", new Object[]{this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex.getMessage()});
        }
    }
}
