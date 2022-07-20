package com.example.anagrammer;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Random;

public class Anagram {
    public static final Random RANDOM = new Random();
    public static final String[] WORDS = {"data", "integer","variable","string","float",
            "percent", "colon","number","array", "boolean", "char",
            "return", "break", "switch", "for", "loop", "else", "if"};


    public static String randomWord() {
        return  WORDS[RANDOM.nextInt(WORDS.length)];
    }
    public static String shuffleWord(String word){
        if (word != null && !"".equals(word)){


            char a[] = word.toCharArray();

            for(int i = 0; i < a.length; i++){

                int j = RANDOM.nextInt(a.length);
                char tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;



            }
            return new String(a);
        }
        return word;
    }

}
