package puglia.saliminerali.commands.prefix;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import puglia.saliminerali.registries.AbstractPrefixCommand;
import puglia.saliminerali.registries.GenericCommandUtil;

public class PrefixGuildInfoCommand extends AbstractPrefixCommand {
	@Override
	public String getName() {
		return "guild-info";
	}

	@Override
	public String getDescription() {
		return "Shows guild info";
	}

	@Override
	public boolean isDatabaseDependent() {
		return false;
	}

	@Override
	public void resolveCommand(MessageReceivedEvent event, String[] args) {
		MessageEmbed messageEmbed = GenericCommandUtil.buildGuildInfoEmbed(event)
				.setDescription("Test message")
				.build();
		event.getMessage().reply(MessageCreateData.fromEmbeds(messageEmbed))
				.queue();
	}
}
