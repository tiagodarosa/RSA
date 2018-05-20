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
            // Dúvida: pode usar a função modPow (potência modular)?
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

    public static BigInteger calculateMDC(BigInteger number) {
        if(number.compareTo(BigInteger.ZERO) == 0){
            return number;
        }
        BigInteger mdc = number.subtract(BigInteger.TWO);
        while(mdc.compareTo(BigInteger.ONE)!=0) {
            if(number.divideAndRemainder(mdc)[1].compareTo(BigInteger.ONE) == 0) break;
            mdc = mdc.subtract(BigInteger.ONE);
        }
        return mdc;
    }

    public static BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e,n);
    }

    public static BigInteger decrypt(BigInteger encryptedMessage, BigInteger d, BigInteger n) {
        return encryptedMessage.modPow(d,n);
    }

    public static BigInteger euclidesExtended(BigInteger a, BigInteger b, BigInteger c) {
        BigInteger r;
        r = b.remainder(a);
        if (r.compareTo(BigInteger.ZERO) == 0) {
            return ((c.divide(a)).remainder(b.divide(a)));
        }
        return ((euclidesExtended(r, a, c.negate()).multiply(b).add(c)).divide((a.remainder(b))));
    }

    public static void main(String[] args) {
        System.out.printf("---%nRSA%n---%n");
        //BigInteger p = new BigInteger("11");
        BigInteger p = generatePrimeNumber();
        System.out.printf("p: %s%n", p.toString());
        //BigInteger q = new BigInteger("13");
        BigInteger q = generatePrimeNumber();
        System.out.printf("q: %s%n", q.toString());
        BigInteger n = p.multiply(q);
        System.out.printf("n: %s%n", n.toString());

        //Para e, podemos calcular o primo relativo de p e q ou utilizar algum outro primo
        //BigInteger e = calculateMDC(p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
        BigInteger e = generatePrimeNumber();
        System.out.printf("e: %s%n", e.toString());
        //Pode usar o euclides estendido para gerar o d
        //BigInteger d = euclidesExtended(e, (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))), BigInteger.ONE));
        BigInteger d = e.modInverse(p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
        System.out.printf("d: %s%n", d.toString());

        System.out.printf("---%n");
        System.out.printf("Chave pública = (%s,%s)%n", e.toString(), n.toString());
        System.out.printf("Chave privada = (%s,%s)%n", d.toString(), n.toString());

        System.out.printf("---%n");
        BigInteger message = new BigInteger("12345678901234567");
        System.out.printf("Mensagem: %s%n", message.toString());
        BigInteger encryptedMessage = encrypt(message, e, n);
        System.out.printf("Mensagem criptografada: %s%n", encryptedMessage.toString());
        BigInteger decryptedMessage = decrypt(encryptedMessage, d, n);
        System.out.printf("Mensagem descriptografada: %s%n", decryptedMessage.toString());

    }
}


