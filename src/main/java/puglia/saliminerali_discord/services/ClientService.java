package puglia.saliminerali_discord.services;

import club.minnced.discord.webhook.WebhookClient;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import puglia.saliminerali.SaliMineraliPlugin;
import puglia.saliminerali_discord.services.utils.IClientServiceUtils;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.time.Instant;
import java.util.List;

public class ClientService implements IClientService {

	private JDA client;
	private IClientServiceUtils clientServiceUtils;

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
		this.clientServiceUtils = new ClientServiceUtils(this.client);
	}


	@Override
	public JDA getClientService() {
		return this.client;
	}

	@Override
	public void disposeClientService() {
		this.client.shutdown();
	}

	@Override
	public IClientServiceUtils getClientServiceUtils() {
		return this.clientServiceUtils;
	}

	public static class ClientServiceUtils implements IClientServiceUtils {

		private final JDA client;

		public ClientServiceUtils(JDA client) {
			this.client = client;
			if((this.guildId != 0) && (this.joinLeaveNotifyChannelId != 0)) {
				this.guild = this.client.getGuildById(guildId);
				this.joinLeaveNotifyChannel = this.guild.getTextChannelById(joinLeaveNotifyChannelId);
			}
		}

		@Override
		public void initializeWebhookClients() {
			String BASE_DISCORD_WEBHOOKS_API = "https://discord.com/api/webhooks";
			if(!this.newChatNotifyURLWebhook.startsWith(BASE_DISCORD_WEBHOOKS_API)
					&& !this.advancementNotifyURLWebhook.startsWith(BASE_DISCORD_WEBHOOKS_API)
			    && !this.genericPlayerEventsURLWebhook.startsWith(BASE_DISCORD_WEBHOOKS_API)) {
				this.newChatNotifyWebhook = WebhookClient.withUrl(newChatNotifyURLWebhook);
				this.advancementNotifyWebhook = WebhookClient.withUrl(advancementNotifyURLWebhook);
				this.genericPlayerEventsChannel = WebhookClient.withUrl(genericPlayerEventsURLWebhook);
			}
		}

		private static String MINECRAFT_SIZE_BASE(String path) {
			return String.format("MINECRAFT_SIDE.%s",path);
		}

		// SNOWFLAKES START
		private final long guildId = SaliMineraliPlugin
				.getInstance()
				.getConfig()
				.getLong(MINECRAFT_SIZE_BASE("GUILD"));

		private final long joinLeaveNotifyChannelId = SaliMineraliPlugin
				.getInstance()
				.getConfig()
				.getLong(MINECRAFT_SIZE_BASE("JOIN_LEAVE_NOTIFY"));

		// SNOWFLAKES END

		// WEBHOOK START
		private final String newChatNotifyURLWebhook = SaliMineraliPlugin
				.getInstance()
				.getConfig()
				.getString(MINECRAFT_SIZE_BASE("CHAT_NOTIFY"));

		private final String advancementNotifyURLWebhook = SaliMineraliPlugin
				.getInstance()
				.getConfig()
				.getString(MINECRAFT_SIZE_BASE("ADVANCEMENT_NOTIFY"));

		private final String genericPlayerEventsURLWebhook = SaliMineraliPlugin
				.getInstance()
				.getConfig()
				.getString(MINECRAFT_SIZE_BASE("GENERIC_PLAYER_EVENTS"));

		// WEBHOOK END

		private Guild guild;
		private TextChannel joinLeaveNotifyChannel;
		private WebhookClient genericPlayerEventsChannel;
		private WebhookClient newChatNotifyWebhook;
		private WebhookClient advancementNotifyWebhook;

		@Override
		public Guild getLoadedGuild() {
			return this.guild;
		}

		@Override
		public TextChannel getJoinLeaveNotifyChannel() {
			return this.joinLeaveNotifyChannel;
		}

		@Override
		public WebhookClient getAdvancementNotifyChannel() {
			return this.advancementNotifyWebhook;
		}

		@Override
		public WebhookClient getGenericPlayerEventsChannel() {
			return this.genericPlayerEventsChannel;
		}

		@Override
		public WebhookClient getChatNotifyChannel() {
			return this.newChatNotifyWebhook;
		}

		@Override
		public EmbedBuilder getDefaultEmbedBuilder() {
			return new EmbedBuilder()
					.setAuthor(this.guild.getName(),this.guild.getIconUrl(),this.guild.getIconUrl())
					.setTimestamp(Instant.now())
					.setThumbnail(this.guild.getIconUrl())
					.setFooter(String.format("Sent by %s",
							this.client.getSelfUser().getAsTag()))
					.setColor(Color.BLUE);
		}

		public long getGuildId() {
			return guildId;
		}

		public long getJoinLeaveNotifyChannelId() {
			return joinLeaveNotifyChannelId;
		}

		public String getGenericPlayerEventsChannelId() {
			return genericPlayerEventsURLWebhook;
		}
	}
}
