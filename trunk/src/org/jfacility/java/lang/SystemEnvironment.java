package org.jfacility.java.lang;

import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author luca
 */
public class SystemEnvironment {
    
    public static Map<String,String> getEnv(){
        return System.getenv();
    }
    
    public static String getEnvPath(){
        return System.getenv("PATH");
    }
    
    public static void printAllEnv(){
        Map<String,String> map = getEnv();
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();
            System.out.println(key + " " + map.get(key));
        }
    }
}