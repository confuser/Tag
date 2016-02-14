package me.confuser.tag.commands;

import me.confuser.bukkitutil.commands.BukkitCommand;
import me.confuser.tag.Tag;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TagCommand extends BukkitCommand<Tag> {

  public TagCommand() {
    super("tag");
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (plugin.getTagged() == null) {
      sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Tag" + ChatColor.GRAY + "] nobody is IT, how boring :(");
    } else {
      sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "Tag" + ChatColor.GRAY + "] " + plugin
              .getTagged() + " is IT!");
    }

    return true;
  }

}
