package org.jfacility.java.awt;

//IMPORT JAVA
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**Classe di metodi riusabili del package java.awt
 * 
 * @author luca
 */
public class AWT {
    /**Centra il frame rispetto lo schermo
     * 
     * @param frame finestra da centrare
     */
    public static void centerComponent(Component component) {
        Dimension frameSize = component.getSize();
        int x = (getScreenDimension().width - frameSize.width) / 2;
        int y = (getScreenDimension().height - frameSize.height) / 2;
        component.setLocation(x, y);
    }// end centraFrame

    /**Centra il componente rispetto la finestra
     * 
     * @param component
     * @param window 
     */
    public static void centerComponent(Component component, Window window) {
        int x = (window.getWidth() - component.getWidth()) / 2;
        int y = (window.getHeight() - component.getHeight()) / 2;
        component.setLocation(window.getLocation().x + x,
                window.getLocation().y + y);
    }

    /**
     * Restituisce lista dei font family disponibili
     * 
     * @return lista stringa font
     */
    public static String[] getAvailableFont() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }

    /**Copia nella clipboard il testo
     * 
     * @param text testo da copiare
     */
    public static void setClipboard(String text) {
        StringSelection data = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
    }
    
    /**
    * Get the String residing on the clipboard.
    *
    * @return any text found on the Clipboard; if none found, return an
    * empty String.
    */
    public static String getClipboard() {
        String result = "";
        Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        boolean hasTransferableText =(contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if ( hasTransferableText ) {
            try {
                result = (String)contents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException ex){
            } catch (IOException ex) {}
        }
        return result;
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