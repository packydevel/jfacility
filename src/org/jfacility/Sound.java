package org.jfacility;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Classe di metodi riusabili del package javax.sound
 * 
 * @author luca
 */
public class Sound {
	/**
	 * esegue un suono Supported audio file formats: aif, au, wav
	 * 
	 * @param url
	 *            url
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public static void playUrl(URL url) throws UnsupportedAudioFileException,
			IOException, LineUnavailableException {
		play(AudioSystem.getAudioInputStream(url));
	}

	/**
	 * esegue un suono Supported audio file formats: aif, au, wav
	 * 
	 * @param f
	 *            file
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public static void playFile(File f) throws UnsupportedAudioFileException,
			IOException, LineUnavailableException {
		play(AudioSystem.getAudioInputStream(f));
	}

	/**
	 * Esegue/suona l'audioinputstream
	 * 
	 * @param stream
	 *            audio input stream
	 * @throws LineUnavailableException
	 * @throws IOException
	 */
	private static void play(AudioInputStream stream)
			throws LineUnavailableException, IOException {
		// At present, ALAW and ULAW encodings must be converted
		// to PCM_SIGNED before it can be played
		AudioFormat format = stream.getFormat();
		if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
			format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					format.getSampleRate(), format.getSampleSizeInBits() * 2,
					format.getChannels(), format.getFrameSize() * 2,
					format.getFrameRate(), true); // big endian

			stream = AudioSystem.getAudioInputStream(format, stream);
		}
		// Create the clip
		DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(),
				((int) stream.getFrameLength() * format.getFrameSize()));

		Clip clip = (Clip) AudioSystem.getLine(info);
		// This method does not return until the audio file is completely loaded
		clip.open(stream);
		// Start playing
		clip.start();
	}
}