package puglia.saliminerali.services;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

import javax.security.auth.login.LoginException;

public interface IClientService {

	void initializeClientService() throws LoginException, InterruptedException;
	void initializeSlashCommands(Guild guild);
	void disposeClientService();

	JDA getClientService();
	Guild getGuild();
	Dotenv getEnvironmentSettings();
	EmbedBuilder getDefaultEmbedBuilder(Guild guild);
}
