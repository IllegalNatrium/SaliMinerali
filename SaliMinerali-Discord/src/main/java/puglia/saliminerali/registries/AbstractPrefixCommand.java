package puglia.saliminerali.registries;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class AbstractPrefixCommand {

	public abstract String getName();
	public abstract String getDescription();
	public abstract boolean isDatabaseDependent();
	public abstract void resolveCommand(MessageReceivedEvent event, String[] args);

}
