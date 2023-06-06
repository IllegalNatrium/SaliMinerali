package puglia.saliminerali.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import puglia.saliminerali.SaliMineraliPlugin;

public class PlayerJoinLeaveListener implements Listener {

	private final TextChannel joinLeaveNotifyChannel = SaliMineraliPlugin
			.getClientService()
			.getJoinLeaveNotifyChannel();


	@EventHandler
	public void onPlayerAdded(PlayerJoinEvent event) {
		MessageEmbed messageEmbed = SaliMineraliPlugin.getClientService()
				.getDefaultEmbedBuilder()
				.setDescription(String.format("%s has joined the server! \uD83D\uDE36\u200D\uD83C\uDF2B️",
						event.getPlayer()
								.getName()))
				.build();
		joinLeaveNotifyChannel.sendMessage(MessageCreateData.fromEmbeds(messageEmbed))
				.queue();
	}

	@EventHandler
	public void onPlayerRemove(PlayerQuitEvent event) {
		MessageEmbed messageEmbed = SaliMineraliPlugin.getClientService()
				.getDefaultEmbedBuilder()
				.setDescription(String.format("%s has left the server! \uD83D\uDE2D️",
						event.getPlayer()
								.getName()))
				.build();
		joinLeaveNotifyChannel.sendMessage(MessageCreateData.fromEmbeds(messageEmbed))
				.queue();
	}

}
