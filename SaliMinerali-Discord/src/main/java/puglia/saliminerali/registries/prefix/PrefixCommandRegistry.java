package puglia.saliminerali.registries.prefix;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import puglia.saliminerali.PugliaSaliMinerali;
import puglia.saliminerali.registries.AbstractPrefixCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PrefixCommandRegistry extends ListenerAdapter implements IPrefixCommandRegistry {

	private final List<AbstractPrefixCommand> commands = new ArrayList<>();

	public PrefixCommandRegistry(AbstractPrefixCommand... commands) {
		this.commands.addAll(Arrays.asList(commands));
	}

	@Override
	public Collection<AbstractPrefixCommand> getPrefixCommands() {
		return commands.stream().collect(Collectors.toCollection(() -> this.commands));
	}

	private final String prefix = PugliaSaliMinerali.getClientService()
			.getEnvironmentSettings()
			.get("PREFIX");

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (!event.isFromGuild()) return;
		if (event.getAuthor().isBot()) return;

		String[] args = event.getMessage().getContentRaw().split(" ");

		if (!args[0].startsWith(prefix)) return;

		String resolvedCommand = args[0].substring(prefix.length());

		String user = (event.getMember().getNickname() != null) ? event.getMember().getNickname()
				: event.getAuthor().getAsTag();

		for(AbstractPrefixCommand command : this.commands) {
			if(resolvedCommand.equals(command.getName())) {
				String[] resolvedArguments = this.resolveArguments(args, args[0]);
				String builtArguments = this.buildArguments(resolvedArguments, " ");
				command.resolveCommand(event, resolvedArguments);
				PugliaSaliMinerali.getPrefixCommandRegistryLogger()
						.info("{} used {} command with arguments\n  [{}]",
								user, command.getName(),builtArguments);
			}
		}
	}

	@Override
	public String[] resolveArguments(String[] input, String item) {
		if(input == null) {
			return null;
		} else if (input.length == 0) {
			return input;
		} else {
			String[] output = new String[input.length-1];
			int count = 0;
			for(String itemInArray : input) {
				if(!itemInArray.equals(item)) {
					output[count++] = itemInArray;
				}
			}
			return output;
		}
	}

	@Override
	public String buildArguments(String[] args, String delimiter) {
		StringBuilder output = new StringBuilder();

		for (String item : args) {
			output.append(item).append(delimiter);
		}

		return output.toString();
	}

}
