package uk.co.joshuawoolley.simpleticketmanager.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import uk.co.joshuawoolley.simpleticketmanager.SimpleTicketManager;
import uk.co.joshuawoolley.simpleticketmanager.command.tickets.ClaimCommand;
import uk.co.joshuawoolley.simpleticketmanager.command.tickets.CloseCommand;
import uk.co.joshuawoolley.simpleticketmanager.command.tickets.CommentCommand;
import uk.co.joshuawoolley.simpleticketmanager.command.tickets.InfoCommand;
import uk.co.joshuawoolley.simpleticketmanager.command.tickets.StatsCommand;
import uk.co.joshuawoolley.simpleticketmanager.command.tickets.TeleportCommand;
import uk.co.joshuawoolley.simpleticketmanager.command.tickets.UnclaimCommand;
import uk.co.joshuawoolley.simpleticketmanager.command.tickets.ViewCommand;
import uk.co.joshuawoolley.simpleticketmanager.ticketsystem.TicketManager;

public class TicketCommandHandler implements TabCompleter, CommandExecutor {
    private HashMap<String, ICommand> commands;

    private SimpleTicketManager plugin;
    private String prefix = "";

    public TicketCommandHandler(SimpleTicketManager pl, TicketManager manager) {
        this.plugin = pl;

        this.prefix = ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("tag"));

        this.commands = new HashMap<String, ICommand>();
        this.commands.put("view", new ViewCommand(pl, manager));
        this.commands.put("info", new InfoCommand(pl, manager));
        this.commands.put("comment", new CommentCommand(pl, manager));
        this.commands.put("claim", new ClaimCommand(pl, manager));
        this.commands.put("teleport", new TeleportCommand(pl, manager));
        this.commands.put("stats", new StatsCommand(pl, manager));
        this.commands.put("unclaim", new UnclaimCommand(pl, manager));
        this.commands.put("close", new CloseCommand(pl, manager));
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                plugin.reloadMessages();
                sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("ticketReload")));
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("mustBePlayer")));
                return true;
            }
        }

        if (args.length <= 0) {
            sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("ticketHelpTitle")));
            if (sender.hasPermission("ticket.view")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("ticketHelp1")));
            } else if (sender.hasPermission("ticket.info.own")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("ticketHelp8")));
            }

            if (sender.hasPermission("ticket.info.own")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("ticketHelp2")));
            }

            if (sender.hasPermission("ticket.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("ticketHelp3")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("ticketHelp4")));
            }

            if (sender.hasPermission("ticket.teleport")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("ticketHelp5")));
            }

            if (sender.hasPermission("ticket.stats")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("ticketHelp6")));
            }

            if (sender.hasPermission("ticket.comment.own")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("ticketHelp7")));
            }

            return true;
        }

        Player player = (Player) sender;
        if (!this.commands.containsKey(args[0].toLowerCase())) {
            return false;
        }

        return this.commands.get(args[0].toLowerCase()).onCommand(player, command, label, args);
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> results = new ArrayList<String>();

        switch (args.length) {
            case 1:
                for (String c : this.commands.keySet())
                    if (args[0].isEmpty() || StringUtil.startsWithIgnoreCase(c, args[0]))
                        results.add(c);
                break;
            case 2:
                if (this.commands.containsKey(args[0].toLowerCase()))
                    return this.commands.get(args[0].toLowerCase()).onTabComplete(sender, command, alias, args);
        }

        return results;
    }
}
