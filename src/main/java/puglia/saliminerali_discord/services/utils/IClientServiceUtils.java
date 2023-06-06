package puglia.saliminerali_discord.services.utils;

import club.minnced.discord.webhook.WebhookClient;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public interface IClientServiceUtils {

	void initializeWebhookClients();
	Guild getLoadedGuild();
	TextChannel getJoinLeaveNotifyChannel();
	WebhookClient getAdvancementNotifyChannel();
	WebhookClient getGenericPlayerEventsChannel();
	WebhookClient getChatNotifyChannel();
	EmbedBuilder getDefaultEmbedBuilder();

}
