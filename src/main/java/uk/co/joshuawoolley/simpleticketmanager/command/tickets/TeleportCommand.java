package uk.co.joshuawoolley.simpleticketmanager.command.tickets;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.joshuawoolley.simpleticketmanager.SimpleTicketManager;
import uk.co.joshuawoolley.simpleticketmanager.command.ICommand;
import uk.co.joshuawoolley.simpleticketmanager.ticketsystem.TicketManager;

public class TeleportCommand implements ICommand {
    private SimpleTicketManager plugin;
    private TicketManager manager;
    
    private String prefix = "";

    public TeleportCommand(SimpleTicketManager pl, TicketManager m) {
        this.plugin = pl;
        this.manager = m;
        
        this.prefix = ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("tag"));
    }

    public boolean onCommand(Player player, Command command, String label, String[] args) {
        if (!player.hasPermission("ticket.teleport")) {
            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("noPermission")));
            return true;
        }
        
        if (args.length > 1) {
            int id = 0;
            try {
                id = Integer.parseInt(args[1]);
            } catch(NumberFormatException e) {
                return false;
            }

            manager.teleportPlayer(player, id);
        } else {
            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("ticketTeleportError")));
        }
        
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
