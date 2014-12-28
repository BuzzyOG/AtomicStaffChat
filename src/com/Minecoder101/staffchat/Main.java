package com.Minecoder101.staffchat;

 import java.io.File;
 import net.md_5.bungee.api.ProxyServer;
 import net.md_5.bungee.api.plugin.PluginManager;
 
 public class Main extends net.md_5.bungee.api.plugin.Plugin
 {
   private Misc misc;
   
   public Main() {}
   
   public void onEnable()
   {
     File df = getDataFolder();
     if (!df.exists()) {
       df.mkdir();
       df.setWritable(true);
       df.setReadable(true);
     }
     if (!new File(getDataFolder(), "conf.yml").exists()) {
      Misc.copy(getResourceAsStream("conf.yml"), new File(getDataFolder(), "conf.yml"));
     }
    if (!new File(getDataFolder(), "lang.yml").exists()) {
       Misc.copy(getResourceAsStream("lang.yml"), new File(getDataFolder(), "lang.yml"));
    }
     
     ConfigManager.loadIntoMemory(getDataFolder());
    
     getProxy().getPluginManager().registerCommand(this, new MainCommand(this));
     getProxy().getPluginManager().registerListener(this, new MainListener(this));
     
     this.misc = new Misc(this);
   }
  
 
  public void onDisable() {}
   
 
   public Misc getMisc()
  {
     return this.misc;
   }
 }