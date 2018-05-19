package com.company;

import java.util.Random;
import java.math.BigInteger;

public class Main {

    public static BigInteger generateRandomNumberUpTo(BigInteger upTo) {
        Random rand = new Random();
        BigInteger result;
        do {
            result = new BigInteger(upTo.bitLength(), rand);
        } while(result.compareTo(upTo) >= 0);
        return result;

    }

    public static BigInteger generateRandomOddNumber() {
        Random random = new Random();
        BigInteger number = new BigInteger(32, random);
        if(number.divideAndRemainder(BigInteger.TWO)[1].compareTo(BigInteger.ZERO) == 0) {
            number = number.add(BigInteger.ONE);
        }
        //System.out.printf("Função generateRandomOddNumber() gerou o número %s%n", number.toString());
        return number;
    }

    public static boolean checkIfIsPrime(BigInteger number) {
        int tentativas = 10;
        if(number.compareTo(BigInteger.TWO) == 0) {
            return false;
        }
        if(number.compareTo(BigInteger.TWO.add(BigInteger.ONE)) == 0) {
            return true;
        }
        BigInteger tentativa;
        for (int i = 1; i <= tentativas; i++)
        {
            BigInteger random = generateRandomNumberUpTo(number);
            tentativa = random.modPow(number.subtract(BigInteger.ONE), number);
            if (tentativa.compareTo(BigInteger.ONE) == 0) {
                //System.out.printf("A tentativa %d mostrou que o número %s possivelmente é primo, %d tentativas restantes.%n", i, number.toString(), tentativas-i);
            }
            else {
                //System.out.printf("Na tentativa %d foi detectado que o número %s não é primo.%n", i, number.toString());
                return false;
            }
        }
        return true;
    }

    public static BigInteger generatePrimeNumber() {
        BigInteger number = generateRandomOddNumber();
        while(checkIfIsPrime(number)==false) {
            number = number.add(BigInteger.TWO);
        }
        return number;
    }

    public static void main(String[] args) {
        BigInteger p = generatePrimeNumber();
        System.out.printf("p: %s%n", generatePrimeNumber().toString());
        BigInteger q = generatePrimeNumber();
        System.out.printf("q: %s%n", generatePrimeNumber().toString());
        BigInteger n = p.multiply(q);
        System.out.printf("n: %s%n", n.toString());
    }
}
