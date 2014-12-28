package com.Minecoder101.staffchat;
 
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import net.md_5.bungee.api.CommandSender;
 import net.md_5.bungee.api.ProxyServer;
 import net.md_5.bungee.api.chat.TextComponent;
 import net.md_5.bungee.api.connection.ProxiedPlayer;
 import net.md_5.bungee.api.plugin.PluginManager;
 import net.md_5.bungee.config.Configuration;
 
 public class Misc
 {
   private static Main instance;
   
   public Misc(Main instance)
   {
     instance = instance;
   }
   
   public void sendMessage(ProxiedPlayer to, ProxiedPlayer sender, String[] parts) {
     String message = "";
     for (String part : parts) message = message + part + " ";
     message = message.substring(0, message.length() - 1);   
 
 
     message = ConfigManager.getConf().getString("format").replace("%server", sender.getServer().getInfo().getName()).replace("%user", sender.getName()).replace("%message", message);
     to.sendMessage(TextComponent.fromLegacyText(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message)));
   }
   
   public void sendLM(CommandSender cs, String s, boolean strip) {
     s = ConfigManager.getLang().getString("prefix") + " " + ConfigManager.getLang().getString(s);
     cs.sendMessage(TextComponent.fromLegacyText(strip ? s : net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', s)));
   }
   
   public void reloadPlugin(CommandSender sender) {
     ConfigManager.loadIntoMemory(instance.getDataFolder());
     instance.getProxy().getPluginManager().unregisterCommands(instance);
     instance.getProxy().getPluginManager().registerCommand(instance, new MainCommand(instance));
     sendLM(sender, "plugin-reloaded-msg", false);
   }
   
   public static void shutdownPlugin() {
     instance.getProxy().getPluginManager().unregisterCommands(instance);
     instance.getProxy().getPluginManager().unregisterListeners(instance);
   }
   
   public static boolean copy(InputStream from, File to) {
     try { InputStream input = from;Throwable localThrowable6 = null;
       try { if ((!to.exists()) && (!to.createNewFile())) throw new IOException("File extraction failed!");
         FileOutputStream output = new FileOutputStream(to);Throwable localThrowable7 = null;
         try { byte[] b = new byte['?'];
           int length;
           while ((length = input.read(b)) > 0) output.write(b, 0, length);
         }
         catch (Throwable localThrowable1)
         {
           localThrowable7 = localThrowable1;throw localThrowable1;
         }
         finally {}
       }
       catch (Throwable localThrowable4)
       {
         localThrowable6 = localThrowable4;throw localThrowable4;
 
 
       }
        finally
       {
 
         if (input != null) if (localThrowable6 != null) try { input.close(); } catch (Throwable localThrowable5) { localThrowable6.addSuppressed(localThrowable5); } else input.close();
       } } catch (IOException e) { e.printStackTrace();
       return false;
     }
     return true;
   }
 }
