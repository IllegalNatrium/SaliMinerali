package puglia.saliminerali.registries.slash;

import puglia.saliminerali.registries.AbstractSlashCommand;

import java.util.Collection;

public interface ISlashCommandRegistry {

	Collection<AbstractSlashCommand> getSlashCommands();

}
