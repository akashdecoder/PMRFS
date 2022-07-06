package com.api.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    public static final String ACCOUNT_SID = "AC99ad5acd06cb5d52c4639d34689e7074";
    public static final String AUTH_TOKEN = "48614c521d9b4910f819c89f1ce9a186";

    public void sendOtpSMS(String toContactNumber, String otp) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(new PhoneNumber("+91"+toContactNumber), new PhoneNumber("+17692413594"), "Your otp is: " + otp).create();
    }

}
