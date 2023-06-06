package puglia.saliminerali.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import puglia.saliminerali.SaliMineraliPlugin;

public class PlayerGenericEventsListener implements Listener {

	private static final WebhookClient genericPlayerEventsWebhook = SaliMineraliPlugin
			.getClientService()
			.getClientServiceUtils()
			.getGenericPlayerEventsChannel();
	private static final String CRAFATAR_URL_BASE = "https://crafatar.com/avatars/%s";
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if (event.getEntity().getKiller() == null) {
			event.setCancelled(true);
			return;
		}
		String message = "%s è stat* killat* da %s\n%s";

		Player killed = event.getEntity();
		Player killer = event.getEntity().getKiller();

		WebhookMessageBuilder webhookMessage = new WebhookMessageBuilder()
				.setUsername(event.getPlayer().getName())
				.setAvatarUrl(String.format(CRAFATAR_URL_BASE, event.getPlayer().getUniqueId()))
				.setContent(String.format(message,
						killed.getName(),
						killer.getName(),
						LegacyComponentSerializer.legacyAmpersand()
								.serialize(event.deathMessage())));
		genericPlayerEventsWebhook.send(webhookMessage.build());
	}

}
