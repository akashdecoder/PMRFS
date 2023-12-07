package com.api.services;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;




@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendMail(String identifier, String name, String message, String recipient) {
        boolean flag = false;
        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                mimeMessage.setFrom(new InternetAddress("akash.ranjan1999@gmail.com"));

                if(identifier.equals("Registration message")) {
                    String body = "<html><body><div><h2>Hello" + name +", </h2><p>Congrats, you are registered. Login to continue to the portal.</p></div></body></html>";
                    mimeMessage.setSubject("Registration Success->" + name);
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setText(body, true);
                } else if(identifier.equals("Fund Request Verification")) {
                    String body = "<html><body><div><h2>Hello Office, </h2><p>Approve the fund request.</p>Click here: <br><br><p><a href=\"http://localhost:8900/login\">http://localhost:8900/login</a> to login to your portal.</p></div></body></html>";
                    mimeMessage.setSubject("Fund Approval: " + message);
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setText(body, true);
                } else if(identifier.equals("Fund Approved")) {
                    String body = "<html><body><div><h2>Hello Office, </h2><p>Your fund request for " + name + " with amount: " + message + " is being approved. </p>Click here: <br><br><p><a href=\"http://localhost:8900/login\">http://localhost:8900/login</a> to login to your portal.</p></div></body></html>";
                    mimeMessage.setSubject("Fund Approval: " + message);
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setText(body, true);
                } else if(identifier.equals("Contribution")) {
                    String body = "<html><body><div><h2>Hello " + name +" , </h2><p>Your Contribution for PMO for" + message + " is being done. </p>Please check your dashboard for the details.</p></div></body></html>";
                    mimeMessage.setSubject("Fund Contribution Success: " + message);
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setText(body, true);
                } else if(identifier.equals("Contribution PMO")) {
                    String body = "<html><body><div><h2>Hello Office, </h2><p>" + name + " has contributed for " + message +".</p>Click here: <br><br><p><a href=\"http://localhost:8900/login\">http://localhost:8900/login</a> to login to your portal.</p></div></body></html>";
                    mimeMessage.setSubject("Fund Contribution: " + message);
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setText(body, true);
                } else if(identifier.equals("Reset Password")) {
                    String body = "<html><body><div><h2>Hello, " + name + "</h2><p>The password reset link is being generated, please reset the password by clicking the following link </p>Click here: <br><br><p><a href=\""+ message +"\"> " + message +"</a> to login to your portal.</p></div></body></html>";
                    mimeMessage.setSubject("Password Reset Link");
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setText(body, true);
                }
            }
        };
        try {
            mailSender.send(mimeMessagePreparator);
            flag = true;
        } catch (MailException e) {
            System.err.println(e.getMessage());
        }
        return flag;
    }
}


