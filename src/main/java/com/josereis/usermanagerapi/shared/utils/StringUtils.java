package com.josereis.usermanagerapi.shared.utils;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;

@Component
public class StringUtils extends org.springframework.util.StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.isBlank();
    }
    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    public static String removeAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static Boolean isBooleanValue(String value) {
        String[] trueValues = new String[]{"true", "sim", "verdadeiro"};
        String[] falseValues = new String[]{"false", "falso"};

        return (Arrays.stream(trueValues).toList().contains(value) || Arrays.stream(falseValues).toList().contains(value));
    }

    public static String removeMask(String value){
        if(value != null) {
            return value.replaceAll("\\D", "");
        }

        return value;
    }

    public static boolean compareNumbers(String a, String b) {
        return removeMask(a).equals(removeMask(b));
    }
}
