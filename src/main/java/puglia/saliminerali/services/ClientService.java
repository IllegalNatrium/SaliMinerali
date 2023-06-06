package puglia.saliminerali.services;

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

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.time.Instant;
import java.util.List;

public class ClientService implements IClientService {

	private JDA client;
	private Guild guild;
	private TextChannel joinLeaveNotifyChannel;
	private TextChannel advancementNewNotifyChannel;
	private TextChannel newRecipeNotifyChannel;
	private TextChannel newChatMessageChannel;

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

		this.guild = this.client.getGuildById(SaliMineraliPlugin
				.getInstance()
				.getConfig()
				.getLong("CHANNELS.GUILD"));
		this.joinLeaveNotifyChannel = this.guild.getTextChannelById(SaliMineraliPlugin
				.getInstance()
				.getConfig()
				.getLong("CHANNELS.JOIN_LEAVE_NOTIFY"));
		this.advancementNewNotifyChannel = this.guild.getTextChannelById(SaliMineraliPlugin
				.getInstance()
				.getConfig()
				.getLong("CHANNELS.ADVANCEMENT_NOTIFY"));
		this.newChatMessageChannel = this.guild.getTextChannelById(SaliMineraliPlugin
				.getInstance()
				.getConfig()
				.getLong("CHANNELS.CHAT_NOTIFY"));
		this.newRecipeNotifyChannel = this.guild.getTextChannelById(SaliMineraliPlugin
				.getInstance()
				.getConfig()
				.getLong("CHANNELS.NEW_RECIPE_NOTIFY"));
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
	public Guild getLoadedGuild() {
		return this.guild;
	}

	@Override
	public TextChannel getJoinLeaveNotifyChannel() {
		return this.joinLeaveNotifyChannel;
	}

	@Override
	public TextChannel getAdvancementNotifyChannel() {
		return this.advancementNewNotifyChannel;
	}

	@Override
	public TextChannel getChatNotifyChannel() {
		return this.newChatMessageChannel;
	}

	@Override
	public TextChannel getNewRecipeNotifyChannel() {
		return this.newRecipeNotifyChannel;
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
}
