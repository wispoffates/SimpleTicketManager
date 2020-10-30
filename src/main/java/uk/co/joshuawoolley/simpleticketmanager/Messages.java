package uk.co.joshuawoolley.simpleticketmanager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
* @author Josh Woolley
*/
public class Messages {
	
	private SimpleTicketManager plugin;
	public HashMap<String, String> messageData;
	
	/**
	 * Constuctor for all Messages
	 * 
	 * @param instance
	 * 			SimpleTicketManager instance
	 */
	public Messages(SimpleTicketManager instance) {
		plugin = instance;
		messageData = new HashMap<String, String>();
	}
	
	/**
	 * Get the Message Data HashMap
	 * 
	 * @return messageData
	 */
	public HashMap<String, String> getMessageData() {
		File f = new File(plugin.getDataFolder() + File.separator + "messages.yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				this.plugin.getLogger().severe("Failure to new the messages.yml file.");
			}
			
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			insertMessages(config);
			
			try {
                config.save(f);
            } catch (IOException e) {
                e.printStackTrace();
                this.plugin.getLogger().severe("Failure to save the messages.yml file.");
            }
		}
		
		return loadMessages();
	}
	
	/**
	 * Load the HashMap with all the latest messages from the config
	 * 
	 * @return messageData
	 */
	public HashMap<String, String> loadMessages() {
		File f = new File(plugin.getDataFolder() + File.separator + "messages.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);

		for (String message : config.getConfigurationSection("").getKeys(false)) {
			messageData.put(message, config.getString(message));
		}

		return messageData;
	}
	
	private void insertMessages(FileConfiguration config) {
	    config.set("tag", "&6[&4Ticket Manager&6]");
	    config.set("createTicket", "&bTicket has successfully been created for you with the ticket id &6%id&b.\n Use this id to add comments &c/ticket comment add <id> <message>&b or view the ticket &c/ticket info <id>&b.");
	    config.set("createComment", "&bComment has successfully been added to ticket &6%id");
	    config.set("adminUpdate", "&bA new report has been submitted! To view report do &c/ticket info %id");
	    config.set("reportHelp", "&bThe following commands are available;");
	    config.set("createHelp", "&b/report <reason> <description>  &6To open a report");
	    config.set("ticketHelpTitle", "&bThe following commands are available");
	    config.set("ticketHelp1", "&b/ticket view <open|asssigned|closed> <all>  &6To view which tickets are open");
	    config.set("ticketHelp2", "&b/ticket info <ticket id>  &6To view more information about a report");
	    config.set("ticketHelp3", "&b/ticket claim <ticket id>  &6Claim a ticket for you to resolve");
	    config.set("ticketHelp4", "&b/ticket close <ticket id>  &6Close a ticket once you resolved the report");
		config.set("ticketHelp5", "&b/ticket teleport <ticket id>  &6Teleport to the location of the report");
		config.set("ticketHelp6", "&b/ticket stats  &6View how many reports are open, assigned or closed");
		config.set("ticketHelp7", "&b/ticket comment <ticket id> <message> &6Add comments to existing tickets");
		config.set("ticketHelp8", "&b/ticket view &6To view all of your tickets");
		config.set("noId", "&bNo ticket with that ID");
		config.set("claimTicket", "&bYou have successfully claimed this ticket");
		config.set("alreadyClaimed", "&bThis ticket has already been claimed");
		config.set("alreadyClosed", "&bThis ticket has already been closed");
		config.set("alreadyUnclaimed", "&bThis ticket can't be unclaimed");
		config.set("closeTicket", "&bYou have successfully closed this ticket");
		config.set("unclaimTicket", "&bYou have successfully unclaimed this ticket");
		config.set("teleport", "&bYou have been teleported to the location of the report");
		config.set("closeNotice", "&bTicket &6%id&b has just been closed by &6%player");
		config.set("ticketTitle", "&b==============&4Information&b==============");
		config.set("ticketId", "&aTicket ID: &6");
		config.set("ticketStatus", "&aStatus: &6");
		config.set("ticketAssignedTo", "&aAssigned To: &6");
		config.set("ticketClosedBy", "&aClosed By: &6");
		config.set("ticketClosedDate", "&aClosed Date: &6");
		config.set("ticketReportingPlayer", "&aReporting Player: &6");
		config.set("ticketReason", "&aReason: &6");
		config.set("ticketDescription", "&aDescription: &6");
		config.set("ticketAmount", "&aAmount of Players online: &6");
		config.set("ticketServer", "&aServer: &6");
		config.set("ticketDateCreated", "&aDate of report: &6");
		config.set("ticketLocation", "&aLocation of report: &6");
		config.set("ticketComments", "&aComments: &6%amount comment(s)");
		config.set("ticketFooter", "&b=====================================");
		config.set("statsTitle", "&b==============&4Stats&b==============");
		config.set("statsOpen", "&aOpen: &6");
		config.set("statsAssigned", "&aAssigned: &6");
		config.set("statsClosed", "&aClosed: &6");
		config.set("statsFooter", "&b================================");
		config.set("openTitle", "&b==============&4Open Tickets&b==============");
		config.set("openId", "&aTicket ID: &6");
		config.set("openInfo", "&aDo /ticket info <number>  To view more info on a report");
		config.set("openClaim", "&aDo /ticket claim <number>  To assign the ticket to yourself");
		config.set("openFooter", "&b=======================================");
		config.set("assignedTitle", "&b==============&4Assigned Tickets&b==============");
		config.set("assignedId", "&aTicket ID: &6");
		config.set("assignedInfo", "&aDo /ticket info <number>  To view more info on a report");
		config.set("assignedFooter", "&b=======================================");
		config.set("allAssignedTitle", "&b==============&4All Assigned Tickets&b==============");
		config.set("allAssignedId", "&aTicket ID: &6%id &ais assigned to &6%player");
		config.set("allAssignedInfo", "&aDo /ticket info <number>  To view more info on a report");
		config.set("allAssignedFooter", "&b=======================================");
		config.set("closedTitle", "&b==============&4Closed Tickets&b==============");
		config.set("closedId", "&aTicket ID: &6");
		config.set("closedInfo", "&aDo /ticket info <number>  To view more info on a report");
		config.set("closedFooter", "&b=======================================");
		config.set("allClosedTitle", "&b==============&4All Closed Tickets&b==============");
		config.set("allClosedId", "&aTicket ID: &6%id &awas closed by &6%player");
		config.set("allClosedInfo", "&aDo /ticket info <number>  To view more info on a report");
		config.set("allClosedFooter", "&b=======================================");
		config.set("commentsTitle", "&b==============&4Comments for Ticket %id&b==============");
		config.set("comment", "&6%player: &b%comment");
		config.set("commentsFooter", "&b==============================================");
		config.set("openNoTickets", "&6No Open tickets");
		config.set("assignedNoTickets", "&6You have no assigned tickets");
		config.set("allAssignedNoTickets", "&6No assigned tickets");
		config.set("closedNoTickets", "&6You have no closed tickets");
		config.set("allClosedNoTickets", "&6No closed tickets");
		config.set("noComments", "&6No comments for this ticket");
		config.set("loginNotice", "&bThere are currently %amount open tickets that need assigning!");
		config.set("createFailed", "&bFailed to create ticket. Please contact a Admin if this problem continues");	
		config.set("failClaimTicket", "&bFailed to claim this ticket, please contact a admin if this continues to happen!");
		config.set("failCloseTicket", "&bFailed to close this ticket, please contact a admin if this continues to happen!");
		config.set("ticketInfoError", "&4ERROR! You must enter /ticket info <id>");
		config.set("ticketClaimError", "&4ERROR! You must enter /ticket claim <id>");
		config.set("ticketCloseError", "&4ERROR! You must enter /ticket close <id>");
		config.set("ticketTeleportError", "&4ERROR! You must enter /ticket teleport <id>");
		config.set("ticketReload", "&bYou have successfully reloaded the configs!");
		config.set("noPermission", "&4You do not have permission to use this command!");
		config.set("mustBePlayer", "&4You must be a player to use this command");
		config.set("maxTickets", "&cYou have reached the max amount of tickets you can claim");
		config.set("onlyYourTickets", "&bYou can view only your own tickets");
		config.set("commentOnlyYourTickets", "&bYou can only comment on your own tickets");
	}
}
