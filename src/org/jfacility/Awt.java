package org.jfacility;

//IMPORT JAVA
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.StringSelection;

/**
 * Classe di metodi riusabili del package java.awt
 * 
 * @author luca
 */
public class Awt {
	/**
	 * Centra il frame rispetto lo schermo
	 * 
	 * @param frame
	 *            finestra da centrare
	 */
	public static void frameCentered(Window frame) {
		Dimension frameSize = frame.getSize();
		frame.setSize(frameSize);
		frame.setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width - frameSize.width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - frameSize.height) / 2);
	}// end centraFrame

	/**
	 * Restituisce lista dei font family disponibili
	 * 
	 * @return lista stringa font
	 */
	public static String[] getAvailableFont() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
	}

	/**
	 * Copia nella clipboard il testo
	 * 
	 * @param text
	 *            testo da copiare
	 */
	public static void setClipboard(String text) {
		StringSelection data = new StringSelection(text);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(data, data);
	}

	/**
	 * Restituisce la dimensione dello schermo
	 * 
	 * @return oggetto Dimension
	 */
	public static Dimension getScreenDimension() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
}