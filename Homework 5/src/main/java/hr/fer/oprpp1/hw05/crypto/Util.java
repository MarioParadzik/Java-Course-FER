package hr.fer.oprpp1.hw05.crypto;

/**
 * This class is used as a utility which has two static methods that transform data.
 */
public class Util {

	/**
	 * This private static method is used for transforming a String into byte array.
	 * @param keyText String
	 * @return Byte array.
	 */
	public static  byte[] hextobyte(String keyText) {
		int len = keyText.length();
		if(len == 0) return new byte[0];
		
		if(len % 2 == 1) throw new IllegalArgumentException("Key size must be even.");
		
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
	                             + Character.digit(keyText.charAt(i+1), 16));
	    }
	    
	    return data;
	}
	
	/**
	 * This method is used to transform byte array into String.
	 * @param bytearray Byte array.
	 * @return String.
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytearray) sb.append(String.format("%02x", b));
	  
		return sb.toString();
	}
}
