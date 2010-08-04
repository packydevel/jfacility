package org.lp.jfacility;
//IMPORT JAVA.IO
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**Classe di metodi riusabili del package java.io
 *
 * @author luca
 */
public class Io {
    /**Sposta il file
     *
     * @param from file da spostare
     * @param path percorso destinazione
     */
    public static void moveFile(File from, String path) throws IOException{
        File dir = new File(path);
        if (dir.exists()){
            File newFile =new File(dir, from.getName());
            if (!from.renameTo(newFile)){
                copyFile(from, newFile);
                from.delete();
            }
        } else
            throw new IOException("path not exists");
    }
    /**Sposta e rinomina il file
     *
     * @param from file da spostare
     * @param path percorso destinazione
     * @param name nuovo nome file
     */
    public static void moveFile(File from, String path, String name){
        File dir = new File(path);
        from.renameTo(new File(dir, name));
    }
    /**Copia un file
     *
     * @param src File sorgente
     * @param dst File destinazione
     * @throws IOException
     */
    public static void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    /**Effettua il download dell'inputStream sotto forma di file
     *
     * @param is http content-stream
     * @param f file di riferimento su cui mandare il flusso di inputstream
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void downloadSingle(InputStream is, File f) throws FileNotFoundException,
            IOException {
        OutputStream out = new FileOutputStream(f);
        byte buf[] = new byte[1024];
        int len;
        while ((len = is.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.flush();
        out.close();
        is.close();
    }
}