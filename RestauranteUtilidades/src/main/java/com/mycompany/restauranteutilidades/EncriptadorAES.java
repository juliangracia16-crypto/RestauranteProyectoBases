package com.mycompany.restauranteutilidades;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase de Utileria para encriptar y desencriptar 
 * los telefonos de los clientes registrados
 * @author Julian
 */
public class EncriptadorAES {
    private static SecretKeySpec secretKey;

    private static void prepararClave(String clave) {
        try {
            byte[] key = clave.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            throw new RuntimeException("Error al preparar la clave: " + e.getMessage());
        }
    }

    public static String encriptar(String texto) {
        try {
            String claveSecreta = ConfigManager.getProperty("clave.encriptacion");
            prepararClave(claveSecreta);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] textoEncriptado = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(textoEncriptado);

        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar: " + e.getMessage());
        }
    }

    public static String desencriptar(String textoEncriptado) {
        try {
            String claveSecreta = ConfigManager.getProperty("clave.encriptacion");
            prepararClave(claveSecreta);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] textoDecodificado = Base64.getDecoder().decode(textoEncriptado);
            return new String(cipher.doFinal(textoDecodificado), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar: " + e.getMessage());
        }
    }
}
