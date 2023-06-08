package puglia.saliminerali.registries;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import puglia.saliminerali.PugliaSaliMinerali;

import java.util.stream.Collectors;

public class GenericCommandUtil {

	public static <T extends GenericEvent> EmbedBuilder buildGuildInfoEmbed(T genericEvent) {
		if(genericEvent instanceof MessageReceivedEvent event) {
			Guild guild = event.getGuild();
			return buildGuildInformation(guild);
		} else if(genericEvent instanceof SlashCommandInteractionEvent event) {
			if(!event.isFromGuild())
				return new EmbedBuilder()
						.setDescription("DM Command.");
			Guild guild = event.getGuild();
			return buildGuildInformation(guild);
		}
		return PugliaSaliMinerali.getClientService().getDefaultEmbedBuilder(PugliaSaliMinerali.getClientService().getGuild());
	}

	private static EmbedBuilder buildGuildInformation(Guild guild) {
		String ownerName = (guild.getOwner().getNickname() != null) ? guild.getOwner().getNickname()
		: guild.getOwner().getUser().getAsTag();
		return PugliaSaliMinerali.getClientService()
				.getDefaultEmbedBuilder(guild)
				.addField("Guild-Information-Base",String.format("""
						**Name**: %s
						**Owner**: %s | %s
						**IconUrl**: [Image](%s)
						**BannerUrl**: [Image](%s)
						**SplashUrl**: [Image](%s)
						""", guild.getName(),ownerName,guild.getOwnerIdLong(),
						guild.getIconUrl(),
						guild.getBannerUrl(),
						guild.getSplashUrl()),false)
				.addField("Guild-Information-Public-Settings",String.format("""
						**Default-Notification-Level**: %s
						**Two-Factor-Authentication-Level**: %s
						**Explicit-Content-Level**: %s - %s
						**Verification-Level**: %s
						**Not-Safe-For-Work-Level ( NSFW )**: %s
						**Premium-Tier**: %s
						""", guild.getDefaultNotificationLevel()
						.getKey(),
						guild.getRequiredMFALevel()
						.getKey(),
						guild.getExplicitContentLevel()
						.getKey(),
						guild.getExplicitContentLevel()
						.getDescription(),
						guild.getVerificationLevel()
						.getKey(),
						guild.getNSFWLevel()
						.getKey(),
						guild.getBoostTier()
						.getKey()),true)
				.addField("Guild-Information-Numbers",String.format("""
						**Total-Members**: %s
						**Online-Members**: %s
						**Members-InVoice**: %s
						**Members-Boosting**: %s
						**Stickers-Loaded**: %s
						**Emotes-Loaded**: %s
						**Groups-Created ( Categories )**: %s
						**TextChannels-Created ( TextChannels )**: %s
						**VoiceChannels-Created ( VoiceChannels )**: %s
						**Threads-Created ( ThreadChannels - PUBLIC )**: %s
						**Threads-Created ( ThreadChannels - PRIVATE )**: %s
						**Channels-Created ( Total )**: %s
						""",
						guild.getMemberCount(),
						guild.getMembers()
								.stream()
								.filter((user) -> user.getOnlineStatus().equals(OnlineStatus.ONLINE))
								.collect(Collectors.toList())
								.size(),
						guild.getMembers()
								.stream()
								.filter((user) -> user.getVoiceState().inAudioChannel())
								.toList()
								.size(),
						guild.getMembers()
								.stream()
								.filter(Member::isBoosting)
								.toList()
								.size(),
						guild.getStickers()
								.size(),
						guild.getEmojis()
								.size(),
						guild.getChannels(true)
								.stream()
								.filter((channel) -> channel.getType() == ChannelType.CATEGORY)
								.toList()
								.size(),
						guild.getChannels(true)
								.stream()
								.filter((channel) -> channel.getType() == ChannelType.TEXT)
								.toList()
								.size(),
						guild.getChannels(true)
								.stream()
								.filter((channel) -> channel.getType() == ChannelType.VOICE)
								.toList()
								.size(),
						guild.getChannels(true)
								.stream()
								.filter((channel) -> channel.getType() == ChannelType.GUILD_PUBLIC_THREAD)
								.toList()
								.size(),
						guild.getChannels(true)
								.stream()
								.filter((channel) -> channel.getType() == ChannelType.GUILD_PRIVATE_THREAD)
								.toList()
								.size(),
						guild.getChannels(true)
								.size()),true);
	}

	/*
	 Guild-Information-Base:
	  **Name**: STR
	  **Owner**: OBJ
	   **Name**: STR
	   **ID**: U64
	  **IconUrl**: STR [Image](link)
	  **BannerUrl**: STR [Image](link)
	  **InviteUrl**: STR [Image](link)
	 Guild-Information-Public-Settings:
	 **Default-Notification-Level**: Short
	  **Two-Factor-Authentication-Level**: Short
	  **Explicit-Content-Level**: Short
	  **Verification-Level**: Short
	  **Not-Safe-For-Work-Level ( NSFW )**: Short
	  **Premium-Tier**: Short
	 Guild-Information-Numbers:
	  **Total-Members**: U32
	  **Online-Members**: U32
	  **Members-InVoice**: U32
	  **Members-Boosting**: U32
	  **Stickers-Loaded**: U32
	  **Emotes-Loaded**: U32
	  **Groups-Created ( Categories )**: U32
	  **TextChannels-Created ( TextChannels )**: U32
	  **VoiceChannels-Created ( VoiceChannels )**: U32
	  **Threads-Created ( ThreadChannels - PUBLIC )**: U32
	  **Threads-Created ( ThreadChannels - PRIVATE )**: U32
	  **Channels-Created ( Total )**: U32

	*/


}
