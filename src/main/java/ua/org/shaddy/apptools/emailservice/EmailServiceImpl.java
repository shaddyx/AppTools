package ua.org.shaddy.apptools.emailservice;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.org.shaddy.microtools.StringTools;
import ua.org.shaddy.microtools.ThreadTools;
import ua.org.shaddy.microtools.TimerTools;

public class EmailServiceImpl implements EmailService, Runnable{
    private static final String HTML_TYPE = "text/html";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ConcurrentLinkedQueue<EmailMessage> messageQueue = new ConcurrentLinkedQueue<>();
    private final Session session;
    private final EmailServiceConfig config;
    private final AtomicBoolean sending = new AtomicBoolean(false);
    
    private EmailServiceImpl(EmailServiceConfig config){
        this.config = config;
        Properties props = new Properties();
        logger.info("Setting smtp host:" + config.getSmtpHost());
        props.setProperty("mail.smtp.host", config.getSmtpHost());
        session = Session.getDefaultInstance(props, null);
    }
    
    @Override
    public void sendEmail(EmailMessage message){
        messageQueue.add(message);
        tryToRun();
    }
    
    private void tryToRun(){
        if (sending.compareAndSet(false, true)){
            ThreadTools.startThread(this);
        }
    }
    
    public void run(){
        try{
            if (messageQueue.size() > 0){
                EmailMessage msg;
                while ((msg = messageQueue.poll())!= null){
                    try {
                        sendEmailMessage(msg);
                    } catch (UnsupportedEncodingException | MessagingException e) {
                        throw new MailServiceException("Error sending mail", e);
                    }
                }
            }
        } finally {
            sending.set(false);
        }
    }
    
    private void sendEmailMessage(EmailMessage message) throws MessagingException, UnsupportedEncodingException {
        logger.info("Sending email:" + message);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(config.getFromAddress()));
        String[] addresses = StringTools.split(message.getTo(), Constants.NOTIFICATION_ADDRESSES_DELIMITER);
        for (String address: addresses){
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
        }
        msg.setSubject(message.getSubject());
        if (message.isHtml()){
            Multipart mp = new MimeMultipart();
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(message.getText(), HTML_TYPE);
            mp.addBodyPart(htmlPart);
            msg.setContent(mp);
        } else {
            msg.setText(message.getText());
        }
        Transport.send(msg);
    }

    @Override
    public void stop() {
        
    }

}
