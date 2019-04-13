package uk.co.joshuawoolley.simpleticketmanager.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface ICommand {
    public boolean onCommand(Player player, Command command, String label, String[] args);
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);
}
