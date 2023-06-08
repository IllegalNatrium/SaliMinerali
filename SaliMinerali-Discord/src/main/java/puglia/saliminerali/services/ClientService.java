package puglia.saliminerali.services;

import net.dv8tion.jda.api.JDA;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.Nullable;
import puglia.saliminerali.commands.prefix.PrefixPingCommand;
import puglia.saliminerali.registries.prefix.PrefixCommandRegistry;
import puglia.saliminerali.registries.slash.ISlashCommandRegistry;
import puglia.saliminerali.registries.slash.SlashCommandRegistry;

import javax.security.auth.login.LoginException;
import java.util.List;

public class ClientService implements IClientService {

	private JDA client;
	private final Dotenv DOT_ENV = Dotenv.configure()
			.filename(".env")
			.directory("./assets/")
			.ignoreIfMalformed()
			.ignoreIfMissing()
			.load();

	private final List<GatewayIntent> GATEWAY_INTENTS = List.of(
			GatewayIntent.GUILD_MEMBERS,
			GatewayIntent.GUILD_MESSAGES,
			GatewayIntent.GUILD_MODERATION,
			GatewayIntent.GUILD_VOICE_STATES,
			GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
			GatewayIntent.GUILD_INVITES,
			GatewayIntent.MESSAGE_CONTENT);
	private final ISlashCommandRegistry slashCommandRegistry = new SlashCommandRegistry();

	private final List<CacheFlag> cacheFlags = List.of(CacheFlag.MEMBER_OVERRIDES, CacheFlag.EMOJI, CacheFlag.ROLE_TAGS);

	// not in use
	public static final List<Activity> activityListOrder = List.of(
			Activity.playing("Starting Sali Minerali"),
			Activity.playing("Initializing ClientService"),
			Activity.playing("Initializing PrefixCommandRegistryStage"),
			Activity.playing("Initializing SlashCOmmandRegistryStage"),
			Activity.playing("Sali Minerali - Booted up!"));

	private Guild guild;

	@Override
	public void initializeClientService() throws LoginException, InterruptedException {
		this.client = JDABuilder.createDefault(this.DOT_ENV.get("TOKEN"))
				.setChunkingFilter(ChunkingFilter.ALL)
				.setCompression(Compression.NONE)
				.setStatus(OnlineStatus.DO_NOT_DISTURB)
				.setActivity(Activity.listening("Loading Sali Minerali"))
				.enableCache(cacheFlags)
				.enableIntents(GATEWAY_INTENTS)
				.setAutoReconnect(true)
				.addEventListeners(slashCommandRegistry, new PrefixCommandRegistry(new PrefixPingCommand()))
				.build()
				.awaitReady();

		if(this.DOT_ENV.get("GUILD") != null) {
			this.guild = this.client.getGuildById(Long.parseLong(this.DOT_ENV.get("GUILD")));
			if(this.guild != null) {
				this.initializeSlashCommands(this.guild);
			} else return;
		} else return;
	}

	@Override
	public void initializeSlashCommands(Guild guild) {
		CommandListUpdateAction commandListUpdateAction = guild.updateCommands()
				.addCommands(this.slashCommandRegistry.getSlashCommands());
		commandListUpdateAction.complete();
	}

	@Override
	public void disposeClientService() {
		this.client.shutdown();
	}

	@Override @Nullable
	public JDA getClientService() {
		return this.client;
	}

	@Override @Nullable
	public Guild getGuild() {
		return this.guild;
	}

	@Override
	public Dotenv getEnvironmentSettings() {
		return this.DOT_ENV;
	}

}
