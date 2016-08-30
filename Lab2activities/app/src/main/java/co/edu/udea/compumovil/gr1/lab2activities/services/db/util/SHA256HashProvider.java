package co.edu.udea.compumovil.gr1.lab2activities.services.db.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256HashProvider {

    private static SHA256HashProvider instance = null;

    private SHA256HashProvider() {
    }


    public String createHash(String password) throws Exception {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            return String.format("%064x", new java.math.BigInteger(1, digest)).trim();
        }catch(UnsupportedEncodingException | NoSuchAlgorithmException e){
            throw new Exception(e.getMessage(), e);
        }
    }


    public boolean verifyPassword(String password, String storedHash) throws Exception {
        try{
            String challengeHash = createHash(password);
            return challengeHash.equals(storedHash.trim());
        }catch(Exception e){
            throw e;
        }
    }

    public static SHA256HashProvider getProvider() {
        if(instance == null){
            return (instance = new SHA256HashProvider());
        }
        return instance;
    }

}