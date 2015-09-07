package ua.org.shaddy.apptools.emailservice;

public class EmailMessage {
    private final String subject;
    private final String text;
    private final String to;
    private final boolean html;
    /**
     * by default - message is html
     * @param subject
     * @param text
     */
    public EmailMessage(String to, String subject, String text) {
        super();
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.html = true;
    }
    
    public EmailMessage(String to, String subject, String text, boolean html) {
        super();
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.html = html;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public boolean isHtml() {
        return html;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "EmailMessage [subject=" + subject + ", text=" + text + ", to=" + to + ", html=" + html + "]";
    }
    
}
