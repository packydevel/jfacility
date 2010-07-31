package org.lp.myUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**Classe di metodi riusabili del package java.net
 *
 * @author luca
 */
public class Net {
    /**pinga un host
     *
     * @param host indirizzo da pingare
     * @param timeout valore in millisecondi
     * @return risposta dal ping
     * @throws UnknownHostException
     * @throws IOException
     */
    public static boolean ping(String host, int timeout) throws UnknownHostException, IOException{
        InetAddress address = InetAddress.getByName(host);
        return address.isReachable(timeout);
    }


}
