package com.msg.laza.project.property;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
   private static PropertiesLoader propertiesLoader = new PropertiesLoader();
   private int serverPort;
   private String databaseUrl;
   private int databasePoolSize;
   private Properties properties = new Properties();

   private PropertiesLoader() {
      getProperties();
   }

   public static PropertiesLoader getPropertiesLoader() {
      return propertiesLoader;
   }

   public void getProperties(){
      try {
         InputStream input  = new FileInputStream("src/main/java/resources/application.properties");
         //InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream("application.properties");
         properties.load(input);
         serverPort = Integer.parseInt(properties.getProperty("server.port"));
         databaseUrl = properties.getProperty("database.url");
         databasePoolSize = Integer.parseInt(properties.getProperty("database.poolSize"));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public int getServerPort() {
      return serverPort;
   }

   public String getDatabaseUrl() {
      return databaseUrl;
   }

   public int getDatabasePoolSize() {
      return databasePoolSize;
   }
}
