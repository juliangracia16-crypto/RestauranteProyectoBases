
package com.mycompany.restauranteutilidades;

import java.io.InputStream;
import java.util.Properties;

/**
 *  Manejador de la clave de encriptacion y desencriptacion 
 *  para los telefonos celulares de los clientes desde el archivo config.properties
 * @author Julian
 */
public class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("No se encontró el archivo config.properties");
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar config.properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
