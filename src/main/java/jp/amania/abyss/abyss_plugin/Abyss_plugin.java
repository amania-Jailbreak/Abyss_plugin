package jp.amania.abyss.abyss_plugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class Abyss_plugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getVelocity().getY() > 15) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 100));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 100));
            player.sendMessage("あなたは上昇負荷 Lv1により一時的にデバフを受けました");
        }
    }
}
