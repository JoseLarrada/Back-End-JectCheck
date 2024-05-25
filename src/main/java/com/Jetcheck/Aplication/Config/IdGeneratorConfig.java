package com.Jetcheck.Aplication.Config;

import org.springframework.context.annotation.Configuration;

import java.util.Random;
@Configuration
public class IdGeneratorConfig {
    public String IdGenerator(){
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        StringBuilder cadena= new StringBuilder();
        for (int i = 0; i < 5; i++) {
            char randomChar = (char) ('A' + random.nextInt(26));
            int randomNumber = random.nextInt(10);
            cadena.append(randomNumber).append(randomChar);
        }
        return cadena.toString();
    }
    public String getInitialName(String fullName){
        if (fullName == null || fullName.isEmpty()) {
            return "";
        }
        String[] words = fullName.split("\\s+"); // Divide la cadena por espacios
        StringBuilder initials = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                initials.append(word.charAt(0)); // Toma el primer carácter de cada palabra
            }
        }
        return initials.toString().toUpperCase();
    }
}
