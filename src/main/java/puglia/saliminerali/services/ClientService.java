package puglia.saliminerali.services;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.List;

public class ClientService implements IClientService {

	private JDA client;
	private List<GatewayIntent> enabledIntents = List.of(
			GatewayIntent.GUILD_MEMBERS,
			GatewayIntent.MESSAGE_CONTENT,
			GatewayIntent.GUILD_WEBHOOKS,
			GatewayIntent.GUILD_VOICE_STATES
	);

	@Override
	public void initializeClientService(String token) throws LoginException, InterruptedException {
		this.client = JDABuilder.createDefault(token)
				.enableCache(List.of(CacheFlag.MEMBER_OVERRIDES))
				.enableIntents(enabledIntents)
				.setStatus(OnlineStatus.IDLE)
				.setActivity(Activity.playing("Sali Minerali"))
				.setCompression(Compression.NONE)
				.setChunkingFilter(ChunkingFilter.ALL)
				.build()
				.awaitReady();
	}

	@Override
	public JDA getClientService() {
		return this.client;
	}

	@Override
	public void disposeClientService() {
		this.client.shutdown();
	}
}
