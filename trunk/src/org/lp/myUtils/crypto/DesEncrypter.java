package org.lp.myUtils.crypto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**Criptazione tramite algoritmo DES
 *
 * @author luca
 */
public class DesEncrypter {
    private Cipher ecipher;
    private Cipher dcipher;
    // 8-byte Salt
    byte[] salt = {
        (byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
        (byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03
    };
    // Iteration count
    int iterationCount = 19;

    /**Costruttore
     *
     * @param passPhrase chiave di criptazione
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     */
    public DesEncrypter(String passPhrase) throws NoSuchAlgorithmException, InvalidKeySpecException,
                                        NoSuchPaddingException, InvalidKeyException,
                                        InvalidAlgorithmParameterException {
        // Create the key
        KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance(
            "PBEWithMD5AndDES").generateSecret(keySpec);
        ecipher = Cipher.getInstance(key.getAlgorithm());
        dcipher = Cipher.getInstance(key.getAlgorithm());

        // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        // Create the ciphers
        ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
    }

    /**cripta la stringa
     *
     * @param str stringa da criptare
     * @return stringa criptata
     * @throws UnsupportedEncodingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String encrypt(String str) throws UnsupportedEncodingException,
                                        IllegalBlockSizeException, BadPaddingException {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");
            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);        
    }

    /**Decripta la stringa
     *
     * @param str stringa da decriptare
     * @return stringa decrittata
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String decrypt(String str) throws IOException, IllegalBlockSizeException,
                                        BadPaddingException {
        // Decode base64 to get bytes
        byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
        // Decrypt
        byte[] utf8 = dcipher.doFinal(dec);
        // Decode using utf-8
        return new String(utf8, "UTF8");
    }
}