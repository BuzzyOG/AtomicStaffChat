package com.Minecoder101.staffchat;
 
 import net.md_5.bungee.api.connection.ProxiedPlayer;
 import net.md_5.bungee.api.event.ChatEvent;
 import net.md_5.bungee.api.event.PlayerDisconnectEvent;
 import net.md_5.bungee.api.event.PostLoginEvent;
 import net.md_5.bungee.config.Configuration;
 import net.md_5.bungee.event.EventHandler;
 
 public class MainListener$1 implements net.md_5.bungee.api.plugin.Listener
 {
   private Main instance;
   
   public MainListener$1(Main instance)
   {
     this.instance = instance;
   }
   
   @EventHandler
   public void onChat(ChatEvent event)
   {
     String trueMessage = event.getMessage();
     
     if (trueMessage.startsWith("/")) { return;
     }
     if (!(event.getSender() instanceof ProxiedPlayer)) return;
     ProxiedPlayer sender = (ProxiedPlayer)event.getSender();
     
     if ((!PlayerManager.playerExists(sender)) && (!trueMessage.startsWith("!"))) return;
     if (trueMessage.startsWith("!")) {
       if (ConfigManager.getConf().getBoolean("enable-exclamation-trigger")) {
         if (trueMessage.length() <= 1) return;
         trueMessage = trueMessage.substring(1, trueMessage.length());
       } else { return;
       }
     }
     if (!sender.hasPermission("staffchat.send")) { return;
     }
     event.setCancelled(true);
     
     for (ProxiedPlayer receiver : this.instance.getProxy().getPlayers())
     {
       if (receiver.hasPermission("staffchat.receive"))
       {
         this.instance.getMisc().sendMessage(receiver, sender, trueMessage.split(" "));
       }
     }
   }
   
   @EventHandler
   public void onPlayerLogin(PostLoginEvent event) {
     if (ConfigManager.getConf().getBoolean("enable-persisting-presence")) {
       final ProxiedPlayer p = event.getPlayer();
       
       if (PlayerManager.playerExists(p.getUniqueId()))
       {
         this.instance.getProxy().getScheduler().schedule(this.instance, new Runnable()
         {
 
           public void run() { MainListener$1.this.instance.getMisc().sendLM(p, "user-logged-in-chat", false); } }, 500L, java.util.concurrent.TimeUnit.MILLISECONDS);
       }
     }
   }
   
 
 
   @EventHandler
   public void onPlayerLogout(PlayerDisconnectEvent event)
   {
     if (!ConfigManager.getConf().getBoolean("enable-persisting-presence"))
     {
       if (PlayerManager.playerExists(event.getPlayer()))
       {
         PlayerManager.removePlayer(event.getPlayer());
       }
     }
   }
 }