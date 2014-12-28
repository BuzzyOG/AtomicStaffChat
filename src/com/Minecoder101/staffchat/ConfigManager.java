 package com.Minecoder101.staffchat;
 
 import java.io.File;
 import java.io.IOException;
 import net.md_5.bungee.config.Configuration;
 import net.md_5.bungee.config.ConfigurationProvider;
 import net.md_5.bungee.config.YamlConfiguration;
 
 
 
 
 
 
 public class ConfigManager
 {
   public ConfigManager() {}
      private static Configuration conf = null;
   private static Configuration lang = null;
   public static Configuration getConf() { return conf; }   
   public static Configuration getLang()
   {
     return lang;
   }
   
   public static void loadIntoMemory(File directoryToLook) {
     boolean isInitialization = false;
     if ((conf != null) && (lang != null)) {
       isInitialization = true;
     }
     Configuration conf_lock = null;
     Configuration lang_lock = null;
     if (!isInitialization) {
       conf_lock = conf;
       lang_lock = lang;
     }
     try {
       conf = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(directoryToLook, "conf.yml"));
       
 
       lang = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(directoryToLook, "lang.yml"));
     }
     catch (IOException e)
     {
       e.printStackTrace();
       if (isInitialization) { Misc.shutdownPlugin();
       } else {
         conf = conf_lock;
         lang = lang_lock;
       }
     }
   }
 }