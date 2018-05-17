package com.epam.cafe.util;

import org.apache.logging.log4j.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPasswordUtil {
    private static Logger LOGGER = LogManager.getLogger(HashPasswordUtil.class);
    private HashPasswordUtil(){}

    /**
     * Hashing password used sha1 algorithm.
     * @param input - string for hashing
     * @return hashing password
     */
    public static String sha1(String input){
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            byte[] result = messageDigest.digest(input.getBytes());
            for (int i =0;i< result.length;i++)
            {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.ERROR, "An exception occurred during hashing password: ", e);
        }
        return  sb.toString();
    }
}
