package com.api.utils;

import java.util.HashMap;
import java.util.Map;

public class UniqueIdentifiers {

    public static Map<String, String> aadhaarAddressMap = new HashMap<String, String>() {{
        put("3046 0783 6916", "Jamshedpur, Jharkhand");
        put("3564 8769 3134", "Ranchi, Jharkhand");
        put("4789 6781 3189", "Tumkur, Karnataka");
        put("4599 6781 3189", "Rajasthan, India");
        put("4289 6781 3189", "Dhanbad, Jharkhand");
        put("4999 6781 3189", "Wayanad, Kerala");
        put("3134 6781 3189", "Tirupathi, Andhra Pradesh");
    }};

    public static Map<String, String> incomeProofMap = new HashMap<String, String>() {{
        put("2345671","100000");
        put("3456712","200000");
        put("4567123","100000");
        put("5671234","120000");
        put("6712345","300000");
        put("7123456","600000");
        put("5674321","789000");
    }};

    public static Map<String, String> fundRequestID = new HashMap<String, String>() {{
        put("2345671","13");
        put("3456712","24");
        put("4567123","15");
        put("5671234","19");
        put("6712345","20");
        put("7123456","6");
        put("5674321","7");
    }};

    public static Map<String, String> fundCategoryMap = new HashMap<String, String>() {{
        put("CANCER", "20");
        put("NEURO", "30");
        put("HEART", "10");
        put("Accident", "20");
        put("Flood Damage", "40");
        put("Earthquake Damage", "60");
        put("OTHERS", "5");
    }};

}
