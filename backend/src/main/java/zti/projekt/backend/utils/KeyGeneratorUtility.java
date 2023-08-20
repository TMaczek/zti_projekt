package zti.projekt.backend.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Klasa pomocnicza odpowiedzialna za tworzenie kluczy RSA
 */
public class KeyGeneratorUtility {
    /**
     * Metoda generujaca parę kluczy RSA
     * @return keyPair zawierajaca klucz publiczny i prywatny
     */
    public static KeyPair generateRsaKey(){

        KeyPair keyPair;

        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e){
            throw new IllegalStateException();
        }
        return keyPair;
    }
}
