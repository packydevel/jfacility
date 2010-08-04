package org.lp.jfacility;
//IMPORT JAVA
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.File;
//IMPORT JAVAX
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;
/**Classe di metodi riusabili del package javax.swing
 *
 * @author luca
 */
public class Swing {
    /**Ridimensiona e trasforma in scala di grigi l'immagine usando un oggetto graphics
     * tramite la bufferedimage
     *
     * @param colorImage immagine originale
     * @param width nuova lunghezza
     * @param height nuova larghezza
     * @return immagine riscalata in scala di grigi
     */
    public static ImageIcon grayScaleImage(ImageIcon colorImage, int width, int height) {
        BufferedImage grayScaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayScaleImage.getGraphics();
        g.drawImage(colorImage.getImage(), 0, 0, null);
        g.dispose();
        return new ImageIcon(grayScaleImage);
    }
    /**Ridimensiona l'immagine usando un oggetto graphics2d tramite la bufferedimage
     *
     * @param image immagine originale
     * @param width nuova lunghezza
     * @param height nuova larghezza
     * @return immagine riscalata
     */
    public static ImageIcon scaleImage(ImageIcon image, int width, int height) {
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image.getImage(), 0, 0, width, height, null);
        g2.dispose();
        return new ImageIcon(resizedImg);
    }
    /**Imposta la dimensione fissa della colonna x la tabella
     *
     * @param table tabella
     * @param col colonna da impostare
     * @param width lunghezza
     */
    public static void setTableDimensionLockColumn(JTable table, int col, int width) {
        TableColumn tcol = table.getColumnModel().getColumn(col);
        tcol.setPreferredWidth(width);
        tcol.setMinWidth(width);
        tcol.setMaxWidth(width);
    }
    /**Restituisce la directory scelta tramite GUI JFileChooser
     *
     * @param parent componente di riferimento
     * @param title titolo per il jfilechooser
     * @return percorso assoluto
     */
    public static String getDirectory(Component parent, String title){
        String dir = null;
        JFileChooser jfcDirSub = new JFileChooser();
        jfcDirSub.setDialogTitle(title);
        jfcDirSub.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfcDirSub.setFileHidingEnabled(true);
        jfcDirSub.setMultiSelectionEnabled(false);
        if (jfcDirSub.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
            dir = jfcDirSub.getSelectedFile().getAbsolutePath();
        return dir;
    }
    /**Restituisce il file selezionato tramite GUI JFileChooser
     *
     * @param parent componente di riferimento
     * @param title titolo per il jfilechooser
     * @param fnfe extension filter se si vuole filtrare per estensione
     * @param dir directory di default
     * @return
     */
    public static String getFile(Component parent, String title, 
                                    FileNameExtensionFilter fnfe, File dir){
        String file = null;
        JFileChooser jfcOpen = new JFileChooser();
        jfcOpen.setDialogTitle(title);
        jfcOpen.addChoosableFileFilter(fnfe);
        jfcOpen.setMultiSelectionEnabled(false);
        jfcOpen.setCurrentDirectory(dir);
        if (jfcOpen.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
            file = jfcOpen.getSelectedFile().getAbsolutePath();
        return file;
    }
    /**Restituisce il file selezionato tramite GUI JFileChooser
     *
     * @param parent componente di riferimento
     * @param title titolo per il jfilechooser
     * @param fnfe extension filter se si vuole filtrare per estensione
     * @param dir directory di default
     * @return
     */
    public static String getFile(Component parent, String title,
                                    FileNameExtensionFilter[] fnfe, File dir){
        String file = null;
        JFileChooser jfcOpen = new JFileChooser();
        jfcOpen.setDialogTitle(title);
        for (int i=0; i<fnfe.length; i++)
            jfcOpen.addChoosableFileFilter(fnfe[i]);
        jfcOpen.setMultiSelectionEnabled(false);
        jfcOpen.setCurrentDirectory(dir);
        if (jfcOpen.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
            file = jfcOpen.getSelectedFile().getAbsolutePath();
        return file;
    }

    public static String getTextToolTip(JTable table, int column, JLabel label, String text){
        int availableWidth = table.getColumnModel().getColumn(column).getWidth();
        availableWidth -= table.getIntercellSpacing().getWidth();
        Insets borderInsets = label.getBorder().getBorderInsets(label);
        availableWidth -= (borderInsets.left + borderInsets.right);
        FontMetrics fm = label.getFontMetrics(label.getFont());
        if (fm.stringWidth(text) > availableWidth)
            return text;
        else
            return null;
    }
}