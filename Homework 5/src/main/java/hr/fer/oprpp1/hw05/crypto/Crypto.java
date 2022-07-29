package hr.fer.oprpp1.hw05.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class is used for crypting files and checking the SHA256.
 */
public class Crypto {
	
	/**
	 * Main method.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		if(!(args.length == 2 || args.length == 3)) throw new IllegalArgumentException("Invalid number of arguments.");
		
		Scanner sc = new Scanner(System.in);
		
		if(args[0].equals("checksha")) {
			if(args.length == 3) {
				sc.close();
				throw new IllegalArgumentException("Invalid number of arguments.");
			}
			
			System.out.println("Please provide expected sha-256 digest for " + args[1] + ":");
			System.out.print("> ");
			String S = sc.nextLine();
			
			checkSHA(args[1], S, args[1]);
			
		} else if(args[0].equals("encrypt") || args[0].equals("decrypt")) {
			if(args.length != 3) {
				sc.close();
				throw new IllegalArgumentException("Invalid number of arguments.");
			}
			
			boolean encrypt = args[0].equals("encrypt");
			
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
            System.out.print("> ");
            String keyText = sc.nextLine();
            
            System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
            System.out.print("> ");
            String ivText = sc.nextLine();
            
            cryptoAES(args[1], args[2], encrypt, keyText, ivText);
            
		} else {
			sc.close();
			throw new IllegalArgumentException("Unknown action.");
		}
		
		sc.close();
	}

	/**
	 *This private static method is used for crypting or encrypting files.
	 * @param arg1 Input file path.
	 * @param arg2 Output file path.
	 * @param encrypt encrypt, decrypt.
	 * @param keyText Key.
	 * @param ivText Initialization vector.
	 */
	private static void cryptoAES(String arg1, String arg2, boolean encrypt, String keyText, String ivText) {

		try(InputStream is = new FileInputStream(arg1); 
				OutputStream os = new FileOutputStream(arg2)) {
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			byte[] buff = new byte[4096];
            byte[] bytesOutput = new byte[4096];

            while (true){
            	int r = is.read(buff);
                if(r < 1) break;
                
                bytesOutput = cipher.update(buff, 0, r);
                os.write(bytesOutput);
            }
            
            os.write(cipher.doFinal());
            
            if(encrypt) {
            	System.out.println("Encryption completed. Generated file " + arg2 + " based on file " + arg1 + ".");
            } else {
            	System.out.println("Decryption completed. Generated file " + arg2 + " based on file " + arg1 + ".");
            }
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	/**
	 *This private static method is used the SHA algorithm.
	 * @param path File that we check.
	 * @param s Expected checksum.
	 * @param file file we check.
	 */
	private static void checkSHA(String path, String s, String file) {
		
		try(InputStream is = new FileInputStream(path)) {
			MessageDigest msgdigest = MessageDigest.getInstance("SHA-256");
			byte[] buff = new byte[4096];
			
			while(true) {
				int r = is.read(buff);
				// EOF
				if(r < 1) break;
				msgdigest.update(buff, 0, r);
			}
			
			byte[] realDigest = msgdigest.digest();
			String realDigestString = Util.bytetohex(realDigest);
			
			if(s.equals(realDigestString)) {
				System.out.println("Digesting completed. Digest of " + file + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + file + " does not match the expected digest. Digest");
                System.out.println("was: " + realDigestString);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
