package puglia.saliminerali.registries;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public abstract class AbstractSlashCommand extends CommandDataImpl {

	public AbstractSlashCommand(String name, String description) {
		super(name, description);
	}

	public AbstractSlashCommand(Command.Type type, String name) {
		super(type, name);
	}

	public abstract boolean isDatabaseDependent();
	public abstract void resolveCommand(SlashCommandInteractionEvent event);

}
