package org.jfacility.lang;

import java.io.File;

/**
 * Classe di metodi riusabili del package java.lang
 * 
 * @author luca
 */
public class Lang {
	/**
	 * Converte un intero in stringa
	 * 
	 * @param number
	 *            intero
	 * @return stringa convertita
	 */
	public static String intToString(int number) {
		return String.valueOf(number);
	}

	/**
	 * Converte un double in stringa
	 * 
	 * @param number
	 *            double
	 * @return stringa convertita
	 */
	public static String doubleToString(double number) {
		return String.valueOf(number);
	}

	/**
	 * Converte un long in stringa
	 * 
	 * @param number
	 *            long
	 * @return stringa convertita
	 */
	public static String longToString(long number) {
		return String.valueOf(number);
	}

	/**
	 * Converte un float in stringa
	 * 
	 * @param number
	 *            float
	 * @return stringa convertita
	 */
	public static String floatToString(float number) {
		return String.valueOf(number);
	}

	/**
	 * Converte una stringa in intero
	 * 
	 * @param text
	 *            stringa
	 * @return intero
	 * @throws NumberFormatException
	 */
	public static int stringToInt(String text) throws NumberFormatException {
		return Integer.parseInt(text);
	}

	/**
	 * Converte una stringa in long
	 * 
	 * @param text
	 *            stringa
	 * @return long
	 * @throws NumberFormatException
	 */
	public static long stringToLong(String text) throws NumberFormatException {
		return Long.parseLong(text);
	}

	/**
	 * Converte una stringa in float
	 * 
	 * @param text
	 *            stringa
	 * @return float
	 * @throws NumberFormatException
	 */
	public static float stringToFloat(String text) throws NumberFormatException {
		return Float.parseFloat(text);
	}

	/**
	 * Converte una stringa in double
	 * 
	 * @param text
	 *            stringa
	 * @return double
	 * @throws NumberFormatException
	 */
	public static double stringToDouble(String text)
			throws NumberFormatException {
		return Double.parseDouble(text);
	}

	/**
	 * Converte un decimale in binario
	 * 
	 * @param decimal
	 * @return stringa binaria
	 */
	public static String decimalToBinary(long decimal) {
		return Long.toString(decimal, 2);
	}

	/**
	 * Converte un decimale in ottale
	 * 
	 * @param decimal
	 * @return stringa ottale
	 */
	public static String decimalToOctal(long decimal) {
		return Long.toString(decimal, 8);
	}

	/**
	 * Converte un decimale in esadecimale
	 * 
	 * @param decimal
	 * @return stringa esadecimale
	 */
	public static String decimalToHexadecimal(long decimal) {
		return Long.toString(decimal, 16);
	}

	/**
	 * Converte un decimale in una nuova base
	 * 
	 * @param decimal
	 * @param base
	 * @return stringa valore nella nuova base
	 */
	public static String decimalToAnyBase(long decimal, int base) {
		return Long.toString(decimal, base);
	}

	/**
	 * Converte un binario in decimale
	 * 
	 * @param text
	 *            stringa binaria
	 * @return long decimale
	 * @throws NumberFormatException
	 */
	public static long binaryToDecimal(String text)
			throws NumberFormatException {
		return Long.parseLong(text, 2);
	}

	/**
	 * Converte un ottale in decimale
	 * 
	 * @param text
	 *            stringa ottale
	 * @return long decimale
	 * @throws NumberFormatException
	 */
	public static long octalToDecimal(String text) throws NumberFormatException {
		return Long.parseLong(text, 8);
	}

	/**
	 * Converte un esacedimale in decimale
	 * 
	 * @param text
	 *            stringa esadecimale
	 * @return long decimale
	 * @throws NumberFormatException
	 */
	public static long hexadecimalToDecimal(String text)
			throws NumberFormatException {
		return Long.parseLong(text, 16);
	}

	/**
	 * Converte un numero da una qualsiasi base specificata in decimale
	 * 
	 * @param text
	 *            stringa valore nella vecchia base
	 * @param base
	 *            base di provenienza
	 * @return long decimale
	 * @throws NumberFormatException
	 */
	public static long anyBaseToDecimal(String text, int base)
			throws NumberFormatException {
		return Long.parseLong(text, base);
	}

	/**
	 * Verifica se il sistema operativo in uso è windows
	 * 
	 * @return true = windows, altrimenti false
	 */
	public static boolean isWindows() {
		boolean windows = false;
		String osName = getOsName().toLowerCase();
		if (!osName.equals("linux")) {
			if ((osName.length() > 6)
					&& (osName.substring(0, 7).toLowerCase()
							.equalsIgnoreCase("windows")))
				windows = true;
		}
		return windows;
	}

	/**
	 * Restituisce la cartella di lavoro attuale dell'utente
	 * 
	 * @return stringa percorso
	 */
	public static String getUserDir() {
		return System.getProperty("user.dir");
	}

	/**
	 * 
	 * @return
	 */
	public static File getFileUserDir() {
		return new File(getUserDir());
	}

	/**
	 * Restituisce la cartella home dell'utente
	 * 
	 * @return stringa percorso
	 */
	public static String getUserHome() {
		return System.getProperty("user.home");
	}

	/**
	 * 
	 * @return
	 */
	public static File getFileUserHome() {
		return new File(getUserHome());
	}

	/**
	 * Restituisce il nome del sistema operativo
	 * 
	 * @return stringa nome OS
	 */
	public static String getOsName() {
		return System.getProperty("os.name");
	}

	/**
	 * Restituisce la versione in uso di java
	 * 
	 * @return Stringa versione java
	 */
	public static String getJavaVersion() {
		return System.getProperty("java.version");
	}

	/**
	 * Verifica se il testo non è nullo e non è uguale alla stringa ""
	 * 
	 * @param text
	 *            testo da verificare
	 * @return risultato verifica
	 */
	public static boolean verifyTextNotNull(String text) {
		boolean verify = false;
		if ((text != null) && (!text.equalsIgnoreCase("")))
			verify = true;
		return verify;
	}
}