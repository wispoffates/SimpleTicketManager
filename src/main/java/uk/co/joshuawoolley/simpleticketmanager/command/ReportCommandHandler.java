package uk.co.joshuawoolley.simpleticketmanager.command;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import uk.co.joshuawoolley.simpleticketmanager.SimpleTicketManager;
import uk.co.joshuawoolley.simpleticketmanager.ticketsystem.TicketManager;

public class ReportCommandHandler implements TabCompleter, CommandExecutor {
    private SimpleTicketManager plugin;
    private TicketManager manager;
    private String prefix = "";

    public ReportCommandHandler(SimpleTicketManager pl, TicketManager manager) {
        this.plugin = pl;
        this.manager = manager;
        
        this.prefix = ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("tag"));
    }

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("mustBePlayer")));
            return true;
        }
        
        if (!sender.hasPermission("report.use")) {
            sender.sendMessage(plugin.messageData.get("noPermission"));
            return true;
        }

        Player player = (Player) sender;
        if (args.length > 0) {
            String description = "";
            for (int i = 1; i < args.length; i++) {
                description = description + " " + args[i];
            }

            manager.createTicket(sender, player.getUniqueId().toString(), args[0], description, player.getLocation(), player.getLocation().getWorld().getName(), Bukkit.getOnlinePlayers().size());
        } else {
            sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("reportHelp")));
            sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.messageData.get("createHelp")));
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
