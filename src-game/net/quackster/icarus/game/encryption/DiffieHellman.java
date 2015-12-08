package net.quackster.icarus.game.encryption;

/**
 * Written by Scott Stamp
 * Edited: 11/25/2015 (Scott Stamp).
 * (from Morgoth, thanks Jordan!)
 */
import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {
	
    public int BITLENGTH = 30;

    private BigInteger prime;
    private BigInteger generator;
    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger publicClientKey;
    private BigInteger sharedKey;

    public DiffieHellman() {
        this.init();
    }

    public DiffieHellman(int b) {
        this.BITLENGTH = b;
        this.init();
    }

    public DiffieHellman(BigInteger prime, BigInteger generator) {
        this.prime = prime;
        this.generator = generator;

        this.privateKey = new BigInteger(generateRandomHexString(BITLENGTH), 16);

        if (this.generator.intValue() > this.prime.intValue()) {
            BigInteger temp = this.prime;
            this.prime = this.generator;
            this.generator = temp;
        }

        this.publicKey = this.generator.modPow(this.privateKey, this.prime);
    }

    public static String generateRandomHexString(int len) {
        int rand = 0;
        String result = "";

        Random rnd = new Random();

        for (int i = 0; i < len; i++) {
            rand = 1 + (int) (rnd.nextDouble() * 254); // 1 - 255
            result += Integer.toString(rand, 16);
        }
        return result;
    }

    private void init() {
        this.publicKey = BigInteger.valueOf(0);
        Random random = new Random();
        while (this.publicKey.intValue() == 0) {
            this.prime = BigInteger.probablePrime(BITLENGTH, random);
            this.generator = BigInteger.probablePrime(BITLENGTH, random);

            this.privateKey = new BigInteger(generateRandomHexString(BITLENGTH), 16);

            if (this.privateKey.intValue() < 1) {
                continue;
            }

            if (this.generator.intValue() > this.prime.intValue()) {
                BigInteger temp = this.prime;
                this.prime = this.generator;
                this.generator = temp;
            }

            this.publicKey = this.generator.modPow(this.privateKey, this.prime);
        }
    }

    public void generateSharedKey(String ckey) {
        this.publicClientKey = new BigInteger(ckey);
        this.sharedKey = this.publicClientKey.modPow(this.privateKey, this.prime);
    }

	public BigInteger getPrime() {
		return prime;
	}

	public void setPrime(BigInteger prime) {
		this.prime = prime;
	}

	public BigInteger getGenerator() {
		return generator;
	}

	public void setGenerator(BigInteger generator) {
		this.generator = generator;
	}

	public BigInteger getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(BigInteger privateKey) {
		this.privateKey = privateKey;
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(BigInteger publicKey) {
		this.publicKey = publicKey;
	}

	public BigInteger getPublicClientKey() {
		return publicClientKey;
	}

	public void setPublicClientKey(BigInteger publicClientKey) {
		this.publicClientKey = publicClientKey;
	}

	public BigInteger getSharedKey() {
		return sharedKey;
	}

	public void setSharedKey(BigInteger sharedKey) {
		this.sharedKey = sharedKey;
	}
}