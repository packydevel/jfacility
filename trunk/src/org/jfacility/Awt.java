package org.jfacility;

//IMPORT JAVA
import java.awt.Component;
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

    /**Centra il frame rispetto lo schermo
     * 
     * @param frame finestra da centrare
     */
    public static void frameCentered(Window frame) {
        Dimension frameSize = frame.getSize();
        frame.setSize(frameSize);
        frame.setLocation((getScreenDimension().width - frameSize.width) / 2,
                (getScreenDimension().height - frameSize.height) / 2);
    }// end centraFrame

    public static void centerComponent(Window frame, Component component) {
        System.out.println(frame.getLocation());
        System.out.println(frame.getWidth());
        System.out.println(frame.getHeight());
        int x = (frame.getWidth() - component.getWidth()) / 2;
        int y = (frame.getHeight() - component.getHeight()) / 2;
        component.setLocation(frame.getLocation().x + x, frame.getLocation().y + y);
    }

    /**
     * Restituisce lista dei font family disponibili
     * 
     * @return lista stringa font
     */
    public static String[] getAvailableFont() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }

    /**
     * Copia nella clipboard il testo
     * 
     * @param text
     *            testo da copiare
     */
    public static void setClipboard(String text) {
        StringSelection data = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
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