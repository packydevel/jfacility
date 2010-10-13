package org.jfacility;

//IMPORT JAVA
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.jfacility.lang.Lang;

/**
 * Classe di metodi riusabili del package java.util
 * 
 * @author luca
 */
public class Util {
	/**
	 * Estrae i file contenuti nello zip
	 * 
	 * @param f
	 *            filezip da estrarre
	 * @param except
	 *            stringa/nome da escludere dall'estrazione
	 * @param path
	 *            percorso dove si vuole estrarre
	 * @return arraylist di file estratti
	 * @throws IOException
	 */
	public static ArrayList<File> unzip(File f, String except, String path)
			throws ZipException, IOException {
		ZipFile zipFile = new ZipFile(f);
		return enumerationZip(zipFile, path, except);
	}

	/**
	 * Estrae i file contenuti nello zip
	 * 
	 * @param f
	 *            filezip da estrarre
	 * @param except
	 *            stringa/nome da escludere dall'estrazione
	 * @return arraylist di file estratti
	 * @throws IOException
	 */
	public static ArrayList<File> unzip(File f, String except)
			throws ZipException, IOException {
		ZipFile zipFile = new ZipFile(f);
		return enumerationZip(zipFile, f.getAbsolutePath(), except);
	}

	/**
	 * enumera/analizza il file zip
	 * 
	 * @param zipFile
	 *            filezip da analizzare/enumerare
	 * @param path
	 *            percorso di estrazione
	 * @param except
	 *            stringa/nome da escludere dall'estrazione
	 * @return arraylist di file estratti
	 * @throws IOException
	 */
	private static ArrayList<File> enumerationZip(ZipFile zipFile, String path,
			String except) throws IOException {
		ArrayList<File> alFile = new ArrayList<File>();
		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();
			String zen = zipEntry.getName();
			if (Lang.verifyTextNotNull(zen)
					&& zen.substring(0, except.length()).toLowerCase()
							.equalsIgnoreCase(except.toLowerCase())) {
			} else {
				BufferedInputStream bis = new BufferedInputStream(
						zipFile.getInputStream(zipEntry));
				int index = zen.indexOf(File.separator);
				boolean create = true;
				if (index != -1) {
					if (++index == zen.length())
						create = false;
					else
						zen = zen.substring(index);
				}
				if (create)
					alFile.add(writeZip(bis, path, zen));
			}
		}
		return alFile;
	}

	/**
	 * Effettua la scrittura del file da estrarre dallo zip
	 * 
	 * @param bis
	 * @param path
	 *            percorso
	 * @param name
	 *            nome del file
	 * @return oggetto file estratto
	 * @throws IOException
	 */
	private static File writeZip(BufferedInputStream bis, String path,
			String name) throws IOException {
		File extract = new File(path + name);
		FileOutputStream fos = new FileOutputStream(extract);
		byte[] buffer = new byte[2048];
		BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);
		int size;
		while ((size = bis.read(buffer, 0, buffer.length)) != -1)
			bos.write(buffer, 0, size);
		bos.flush();
		bos.close();
		fos.close();
		bis.close();
		return extract;
	}

	/**
	 * Crea un file zip
	 * 
	 * @param array
	 *            array di file da comprimere
	 * @param outFile
	 *            file zip
	 * @throws IOException
	 */
	public static void createZip(ArrayList<File> array, File outFile)
			throws IOException {
		// Create a buffer for reading the files
		byte[] buf = new byte[1024];
		// Create the ZIP file
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFile));
		// Compress the files
		for (int i = 0; i < array.size(); i++) {
			FileInputStream in = new FileInputStream(array.get(i));
			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(array.get(i).getName()));
			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0)
				out.write(buf, 0, len);
			// Complete the entry
			out.closeEntry();
			in.close();
		}
		// Complete the ZIP file
		out.close();
	}
}