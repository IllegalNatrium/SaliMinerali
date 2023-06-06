package puglia.saliminerali.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import puglia.saliminerali.SaliMineraliPlugin;

public class PlayerAdvancementEarnListener implements Listener {

	private final WebhookClient advancementChannelNotify = SaliMineraliPlugin
			.getClientService()
			.getClientServiceUtils()
			.getAdvancementNotifyChannel();

	private static final String CRAFATAR_URL_BASE = "https://crafatar.com/avatars/%s";
	private static final String MESSAGE_BASE = "[%s] has earned %s\n\n%s";

	@EventHandler
	public void onEarn(PlayerAdvancementDoneEvent event) {
		WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();

		String title = LegacyComponentSerializer.legacyAmpersand()
				.serialize(event.getAdvancement().getDisplay().title());

		String description = LegacyComponentSerializer.legacyAmpersand()
				.serialize(event.getAdvancement().getDisplay().description());

		WebhookMessage build = messageBuilder.setUsername(event.getPlayer().getName())
				.setAvatarUrl(String.format(CRAFATAR_URL_BASE, event.getPlayer().getUniqueId()))
				.setContent(String.format(MESSAGE_BASE, event.getPlayer().getName(), title, description))
				.build();

		advancementChannelNotify.send(build);
	}

}
