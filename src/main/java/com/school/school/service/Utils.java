package com.school.school.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utils {
    private static final int MATRICULE_LENGTH = 8;

    public static String generateMatricule() {
        StringBuilder matricule = new StringBuilder();
        Random random = new Random();

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < MATRICULE_LENGTH; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            matricule.append(randomChar);
        }

        return matricule.toString();
    }

    public static boolean isDateInRange(Date refDate , Date start , Date end){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(start);

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(end);

        return (calendar.compareTo(startDate) >= 0 && calendar.compareTo(endDate) <= 0);

    }
}

