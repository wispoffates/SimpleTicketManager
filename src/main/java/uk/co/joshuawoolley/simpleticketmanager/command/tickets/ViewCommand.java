package uk.co.joshuawoolley.simpleticketmanager.command.tickets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import uk.co.joshuawoolley.simpleticketmanager.SimpleTicketManager;
import uk.co.joshuawoolley.simpleticketmanager.command.ICommand;
import uk.co.joshuawoolley.simpleticketmanager.command.TabOption;
import uk.co.joshuawoolley.simpleticketmanager.ticketsystem.TicketManager;

public class ViewCommand implements ICommand {
    private static final TabOption[] options = new TabOption[] {
            new TabOption("open", "ticket.view.open"),
            new TabOption("assigned", "ticket.view.assigned"),
            new TabOption("closed", "ticket.view.closed")
    };

    private SimpleTicketManager plugin;
    private TicketManager manager;
    
    private String prefix = "";
    
    public ViewCommand(SimpleTicketManager pl, TicketManager m) {
        this.plugin = pl;
        this.manager = m;
        
        this.prefix = ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("tag"));
    }

    @Override
    public boolean onCommand(Player player, Command command, String label, String[] args) {
        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("open")) {
                if (player.hasPermission("ticket.view.open")) {
                    manager.printOpenTickets(player);
                } else {
                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("noPermission")));
                }
            } else if (args[1].equalsIgnoreCase("assigned")) {
                if (args.length > 2) {
                    if (args[2].equalsIgnoreCase("all")) {
                        if (player.hasPermission("ticket.view.assigned.all")) {
                            manager.printAllAssignedTickets(player);
                        } else {
                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("noPermission")));
                        }
                    }
                } else {
                    if (player.hasPermission("ticket.view.assigned")) {
                        manager.printAssignedTickets(player, player.getUniqueId().toString());
                    } else {
                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("noPermission")));
                    }
                }
            } else if (args[1].equalsIgnoreCase("closed")) {
                if (args.length > 2) {
                    if (args[2].equalsIgnoreCase("all")) {
                        if (player.hasPermission("ticket.view.closed.all")) {
                            manager.printAllClosedTickets(player);
                        } else {
                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("noPermission")));
                        }
                    }
                } else {
                    if (player.hasPermission("ticket.view.closed")) {
                        manager.printClosedTickets(player, player.getUniqueId().toString());
                    } else {
                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("noPermission")));
                    }
                }
            }
        } else {
            if (player.hasPermission("ticket.view.open")) {
                manager.printOpenTickets(player);
            } else if (player.hasPermission("ticket.info.own")) {
                manager.printAllCreatedByPlayer(player, player.getUniqueId().toString());
            } else {
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("noPermission")));
            }
        }
        
        return true;
    }

    //       0    1
    //ticket view
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> results = new ArrayList<String>();
        
        switch (args.length) {
            case 2:
                for (TabOption t : options)
                    if (args[1].isEmpty() || (StringUtil.startsWithIgnoreCase(t.getLabel(), args[1]) && sender.hasPermission(t.getPermission())))
                        results.add(t.getLabel());
                break;
        }

        return results;
    }
}
