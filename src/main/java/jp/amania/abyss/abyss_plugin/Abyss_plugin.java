package jp.amania.abyss.abyss_plugin;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public final class Abyss_plugin extends JavaPlugin implements Listener {

    // Startup
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("¥n AbyssPlugin Loaded!!¥n--------------------------¥nAuthor:amania¥nVersion:1.1.0");
        getServer().getPluginManager().registerEvents(this, this);
        startApiCheckTask();
    }

    // Events

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getVelocity().getY() > 15) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 100));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 100));
            player.sendMessage("あなたは上昇負荷 Lv1により一時的にデバフを受けました");
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + player.getName() + "さんが入室しました!!");
        player.sendMessage(ChatColor.AQUA + "amania Serverへようこそ!");
        player.sendMessage(ChatColor.RED + "基本的な禁止事故(チート、荒らし、その他マナーに反する行為)　などが確認された場合それに相応する処置を取る場合があります。");
        player.sendTitle("Welcome To amaniaServer","ようこそ！" , 0, 0, 0);
        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0, 0);
    }

    // Functions

    public void startApiCheckTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    URL url = URI.create("https://auth.amania.jp/minecraft/notice").toURL();
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    conn.disconnect();

                    String message = content.toString();
                    Bukkit.broadcastMessage(ChatColor.YELLOW + message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimer(this, 0L, 1200L); 
    }
}
