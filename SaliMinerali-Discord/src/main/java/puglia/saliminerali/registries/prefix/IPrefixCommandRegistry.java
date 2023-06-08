package puglia.saliminerali.registries.prefix;

import puglia.saliminerali.registries.AbstractPrefixCommand;

import java.util.Collection;

public interface IPrefixCommandRegistry {

	Collection<AbstractPrefixCommand> getPrefixCommands();
	String[] resolveArguments(String[] input, String item);
	String buildArguments(String[] args, String delimited);

}
