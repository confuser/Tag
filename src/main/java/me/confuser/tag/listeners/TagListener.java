package me.confuser.tag.listeners;

import me.confuser.bukkitutil.listeners.Listeners;
import me.confuser.tag.Tag;
import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

public class TagListener extends Listeners<Tag> {

  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onJoin(PlayerJoinEvent event) {
    if (plugin.getTagged() != null) return;

    plugin.setTagged(event.getPlayer().getName());
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    if (!event.getPlayer().getName().equals(plugin.getTagged())) return;

    plugin.pickNewPlayer(event.getPlayer().getName());
  }

  @EventHandler
  public void onAfk(AfkStatusChangeEvent event) {
    if (!event.getAffected().getBase().getName().equals(plugin.getTagged())) return;

    plugin.pickNewPlayer(event.getAffected().getBase().getName());

  }

  @EventHandler
  public void onTag(PlayerInteractEntityEvent event) {
    if (!(event.getRightClicked() instanceof Player)) return;
    if (!event.getPlayer().getName().equals(plugin.getTagged())) return;

    Player tagged = (Player) event.getRightClicked();

    if (plugin.getEssentials().getUser(tagged).isAfk()) {
      event.getPlayer().sendMessage(ChatColor.RED + tagged.getName() + " is AFK and cannot be IT");
      return;
    }

    double max = 2.0D;
    double x = max * (2.0D * Math.random() - 1.0D);
    double z = max * (2.0D * Math.random() - 1.0D);
    double y = max * (0.5D * Math.random() + 0.5D);
    tagged.setVelocity(tagged.getVelocity().add(new Vector(x, y, z)));
    tagged.playEffect(EntityEffect.WITCH_MAGIC);

    plugin.setTagged(tagged.getName());
  }
}
