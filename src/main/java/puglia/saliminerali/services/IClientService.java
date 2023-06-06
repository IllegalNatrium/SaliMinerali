package puglia.saliminerali.services;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import javax.security.auth.login.LoginException;

public interface IClientService {

	void initializeClientService(String token) throws LoginException, InterruptedException;
	JDA getClientService();
	void disposeClientService();

	Guild getLoadedGuild();

	TextChannel getJoinLeaveNotifyChannel();
	TextChannel getAdvancementNotifyChannel();
	TextChannel getChatNotifyChannel();
	TextChannel getNewRecipeNotifyChannel();
	EmbedBuilder getDefaultEmbedBuilder();

}
