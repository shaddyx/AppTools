package ua.org.shaddy.apptools.emailservice;

public class EmailServiceConfig {
    private String smtpHost = "localhost";
    private int smtpPort = 25;
    private String fromAddress = "fromAddress";
    
    public EmailServiceConfig(String smtpHost, int smtpPort, String fromAddress) {
        super();
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.fromAddress = fromAddress;
    }
    
    public String getSmtpHost() {
        return smtpHost;
    }
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }
    public int getSmtpPort() {
        return smtpPort;
    }
    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }
    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    
}
