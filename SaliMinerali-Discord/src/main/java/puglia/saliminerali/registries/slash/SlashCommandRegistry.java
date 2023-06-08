package puglia.saliminerali.registries.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import puglia.saliminerali.PugliaSaliMinerali;
import puglia.saliminerali.registries.AbstractSlashCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SlashCommandRegistry extends ListenerAdapter implements ISlashCommandRegistry {

	private final List<AbstractSlashCommand> commands = new ArrayList<>();

	public SlashCommandRegistry(AbstractSlashCommand... commands) {
		this.commands.addAll(Arrays.asList(commands));
	}

	@Override
	public Collection<AbstractSlashCommand> getSlashCommands() {
		return commands;
	}

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if(!event.isFromGuild()) return;

		if(this.commands.stream().noneMatch((command) -> command.getName().equals(event.getFullCommandName()))) return;

		String user = (event.getMember().getNickname() != null) ? event.getMember().getNickname()
				: event.getUser().getAsTag();

		for(AbstractSlashCommand command : this.commands) {
			if(command.getName().equals(event.getFullCommandName())) {
				PugliaSaliMinerali.getSlashCommandRegistryLogger()
								.info("{} used a slash command {}",user,command.getName());
				command.resolveCommand(event);
			}
		}
	}
}
