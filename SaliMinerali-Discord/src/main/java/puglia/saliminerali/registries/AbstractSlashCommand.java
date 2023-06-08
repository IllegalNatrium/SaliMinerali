package puglia.saliminerali.registries;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public abstract class AbstractSlashCommand implements CommandData {

	public abstract boolean isDatabaseDependent();
	public abstract void resolveCommand(SlashCommandInteractionEvent event);

}
