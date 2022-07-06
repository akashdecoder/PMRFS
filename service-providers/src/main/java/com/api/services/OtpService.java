package com.api.services;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.TimerTask;

@Service
public class OtpService {

    public String generateOTP() {
        String newOTp = "";
        String numbers = "0123456789";
        Random random = new Random();
        for(int i=0; i<6; i++) {
            newOTp += numbers.charAt(random.nextInt(numbers.length()));
        }
        if(newOTp == null) {
            generateOTP();
        }
        return newOTp;
    }

    public void startOtpTimer() {
        try {
            Thread.sleep(100000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Task extends TimerTask {
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}