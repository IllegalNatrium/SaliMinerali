package puglia.saliminerali.commands.prefix;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import net.dv8tion.jda.api.utils.messages.MessageEditData;
import puglia.saliminerali.registries.AbstractPrefixCommand;

import java.time.temporal.ChronoUnit;
public class PrefixPingCommand extends AbstractPrefixCommand {
	@Override
	public String getName() {
		return "ping";
	}

	@Override
	public String getDescription() {
		return "Ping command!";
	}

	@Override
	public boolean isDatabaseDependent() {
		return false;
	}

	@Override
	public void resolveCommand(MessageReceivedEvent event, String[] args) {
		event.getGuildChannel()
				.asTextChannel().sendMessage(MessageCreateData.fromContent("Calculating ms..."))
				.queue((message) -> {
					long ms = event.getMessage().getTimeCreated().until(message.getTimeCreated(), ChronoUnit.MILLIS);
					message.editMessage(MessageEditData.fromContent(String.format("%sms",ms))).queue();
				});
	}
}
