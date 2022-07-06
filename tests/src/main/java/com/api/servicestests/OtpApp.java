package com.api.servicestests;

import com.api.services.OtpService;
import com.api.services.SMSService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class OtpApp {

    public static void main(String[] args) {

    }
}

class OtpGenerator {
    public static String generateOTP() {
        StringBuilder newOTp = new StringBuilder();
        String numbers = "0123456789";
        Random random = new Random();
        for(int i=0; i<6; i++) {
            newOTp.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return newOTp.toString();
    }
}