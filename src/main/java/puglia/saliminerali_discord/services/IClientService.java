package puglia.saliminerali_discord.services;

import club.minnced.discord.webhook.WebhookClient;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import puglia.saliminerali_discord.services.utils.IClientServiceUtils;

import javax.security.auth.login.LoginException;

public interface IClientService {

	void initializeClientService(String token) throws LoginException, InterruptedException;
	JDA getClientService();
	void disposeClientService();
	IClientServiceUtils getClientServiceUtils();


}
