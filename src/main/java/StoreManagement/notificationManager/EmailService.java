package StoreManagement.notificationManager;

public interface EmailService {
    void send(String recipientEmail, String emailBody, String emailSubject);
}
