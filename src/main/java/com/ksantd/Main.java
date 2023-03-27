package com.ksantd;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger counter3 = new AtomicInteger();
    private static final AtomicInteger counter4 = new AtomicInteger();
    private static final AtomicInteger counter5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> checkPalindromes(texts));
        Thread sameLettersThread = new Thread(() -> checkSameLetters(texts));
        Thread increasingLettersThread = new Thread(() -> checkIncreasingLetters(texts));

        palindromeThread.start();
        sameLettersThread.start();
        increasingLettersThread.start();

        palindromeThread.join();
        sameLettersThread.join();
        increasingLettersThread.join();

        System.out.println("Красивых ников с длиной 3: " + counter3.get() + " шт");
        System.out.println("Красивых ников с длиной 4: " + counter4.get() + " шт");
        System.out.println("Красивых ников с длиной 5: " + counter5.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void checkPalindromes(String[] texts) {
        for (String text : texts) {
            if (isPalindrome(text)) {
                checkCounter(text.length());
            }
        }
    }

    public static void checkSameLetters(String[] texts) {
        for (String text : texts) {
            if (hasSameLetters(text)) {
                checkCounter(text.length());
            }
        }
    }

    public static void checkIncreasingLetters(String[] texts) {
        for (String text : texts) {
            if (hasIncreasingLetters(text)) {
                checkCounter(text.length());
            }
        }
    }

    public static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left++) != text.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasSameLetters(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasIncreasingLetters(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void checkCounter(int length) {
        switch (length) {
            case 3 -> counter3.incrementAndGet();
            case 4 -> counter4.incrementAndGet();
            case 5 -> counter5.incrementAndGet();
            default -> {
            }
        }
    }
}