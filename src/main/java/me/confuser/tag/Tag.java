package me.confuser.tag;

import com.earth2me.essentials.Essentials;
import lombok.Getter;
import me.confuser.bukkitutil.BukkitPlugin;
import me.confuser.tag.commands.TagCommand;
import me.confuser.tag.listeners.TagListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Tag extends BukkitPlugin {

  @Getter
  private static Tag plugin;
  private final Random random = new Random();
  @Getter
  private String tagged;
  @Getter
  private Essentials essentials;

  @Override
  public void onEnable() {
    plugin = this;

    essentials = (Essentials) getServer().getPluginManager().getPlugin("Essentials");

    setupConfigs();
    setupCommands();
    setupListeners();
  }

  @Override
  public String getPluginFriendlyName() {
    return "Tag";
  }

  @Override
  public String getPermissionBase() {
    return "tag";
  }

  @Override
  public void setupConfigs() {
  }

  @Override
  public void setupCommands() {
    new TagCommand().register();
  }

  @Override
  public void setupListeners() {
    new TagListener().register();
  }

  @Override
  public void setupRunnables() {
    // TODO Auto-generated method stub

  }

  public void setTagged(String name) {
    tagged = name;

    if (name == null) {
      return;
    }

    getServer()
            .broadcastMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Tag" + ChatColor.GRAY + "] " + name + " is IT!");
  }

  public boolean pickNewPlayer(String exclude) {
    Collection<? extends Player> onlinePlayers = getServer().getOnlinePlayers();

    if (onlinePlayers.size() == 0) {
      tagged = null;
      return false;
    }

    if (onlinePlayers.size() == 1) {
      tagged = onlinePlayers.iterator().next().getName();

      if (tagged.equals(exclude)) {
        tagged = null;
        return false;
      }

      return true;
    }

    ArrayList<String> canBeTagged = new ArrayList<>();

    for (Player player : onlinePlayers) {
      if (player.getName().equals(exclude)) {
        continue;
      }

      if (essentials.getUser(player).isAfk()) {
        continue;
      }

      canBeTagged.add(player.getName());
    }

    if (canBeTagged.size() == 0) {
      tagged = null;
      return false;
    }

    setTagged(canBeTagged.get(random.nextInt(canBeTagged.size())));
    return true;
  }

}
