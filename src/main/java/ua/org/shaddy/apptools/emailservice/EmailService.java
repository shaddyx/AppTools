package ua.org.shaddy.apptools.emailservice;

public interface EmailService {
    /**
     * sends email to recipient
     * @param message
     */
    public void sendEmail(EmailMessage message);
    /**
     * stops the service
     */
    public void stop();
}
