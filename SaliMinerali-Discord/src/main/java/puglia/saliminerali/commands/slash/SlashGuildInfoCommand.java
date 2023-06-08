package puglia.saliminerali.commands.slash;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import puglia.saliminerali.registries.AbstractSlashCommand;
import puglia.saliminerali.registries.GenericCommandUtil;

public class SlashGuildInfoCommand extends AbstractSlashCommand {


	public SlashGuildInfoCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean isDatabaseDependent() {
		return false;
	}

	@Override
	public void resolveCommand(SlashCommandInteractionEvent event) {
		MessageEmbed messageEmbed = GenericCommandUtil.buildGuildInfoEmbed(event)
				.setDescription("Test message")
				.build();
		event.deferReply(false)
				.addEmbeds(messageEmbed)
				.queue();
	}
}
