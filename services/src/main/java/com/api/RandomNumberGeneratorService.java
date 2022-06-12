package com.api;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;

@Service
public class RandomNumberGeneratorService {

    public String generateRandomId() {
        Random r=new Random();
        String number="";
        HashSet<Integer> set= new HashSet<Integer>();
        while(set.size()<1) {
            int ran=r.nextInt(99999)+123456;
            set.add(ran);
        }
        int len = 6;
        String random=String.valueOf(len);
        for(int  random1:set) {
            random=Integer.toString(random1);
            number+=random;

        }
        return number;
    }
}
