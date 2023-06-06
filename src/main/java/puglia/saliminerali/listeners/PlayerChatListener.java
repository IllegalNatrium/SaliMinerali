package puglia.saliminerali.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.receive.ReadonlyMessage;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import puglia.saliminerali.SaliMineraliPlugin;

public class PlayerChatListener implements Listener {

	private final WebhookClient chatChannelNotify = SaliMineraliPlugin
			.getClientService()
			.getClientServiceUtils()
			.getChatNotifyChannel();

	private static final String CRAFATAR_URL_BASE = "https://crafatar.com/avatars/%s";
	private static final String MESSAGE_BASE = "[%s] - %s";

	@EventHandler
	public void onPlayerChat(AsyncChatEvent event) {
		WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();
		String message = LegacyComponentSerializer.legacyAmpersand()
				.serialize(event.message());
		WebhookMessage build = messageBuilder.setUsername(event.getPlayer().getName())
				.setAvatarUrl(String.format(CRAFATAR_URL_BASE, event.getPlayer().getUniqueId()))
				.setContent(String.format(MESSAGE_BASE, event.getPlayer().getName(), message))
				.build();
		chatChannelNotify.send(build);
	}

}
