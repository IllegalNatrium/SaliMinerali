package puglia.saliminerali;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import puglia.saliminerali.services.ClientService;
import puglia.saliminerali.services.IClientService;

import javax.security.auth.login.LoginException;

public class PugliaSaliMinerali {

	private static final IClientService clientService = new ClientService();

	private static  String BASE_LOGGER_PATH(String path, String file) {
		return String.format("puglia.saliminerali.%s.%s",path.toLowerCase(),file);
	}

	private static final String SALI_MINERALI_LOGGER_PATH =
			BASE_LOGGER_PATH("puglia.saliminerali","PugliaSaliMinerali");

	private static final String PREFIX_COMMAND_REGISTRY_LOGGER_PATH =
			BASE_LOGGER_PATH("registries.prefix","PrefixCommandRegistry");
	private static final String SLASH_COMMAND_REGISTRY_LOGGER_PATH =
			BASE_LOGGER_PATH("registries.slash","SlashCommandRegistry");

	private static final String MYSQL_SERVICE_REGISTRY_LOGGER_PATH =
			BASE_LOGGER_PATH("services","MySqlService");

	private static final Logger PREFIX_COMMAND_REGISTRY_LOGGER =
			LoggerFactory.getLogger(PREFIX_COMMAND_REGISTRY_LOGGER_PATH);

	private static final Logger SLASH_COMMAND_REGISTRY_LOGGER =
			LoggerFactory.getLogger(SLASH_COMMAND_REGISTRY_LOGGER_PATH);

	private static final Logger MYSQL_SERVICE_LOGGER =
			LoggerFactory.getLogger(MYSQL_SERVICE_REGISTRY_LOGGER_PATH);

	private static final Logger PUGLIA_SALI_MINERALI_LOGGER =
			LoggerFactory.getLogger(SALI_MINERALI_LOGGER_PATH);

	public static void main(String[] args) throws LoginException, InterruptedException {
		clientService.initializeClientService();
	}

	public static IClientService getClientService() {
		return clientService;
	}

	public static Logger getPrefixCommandRegistryLogger() {
		return PREFIX_COMMAND_REGISTRY_LOGGER;
	}

	public static Logger getSlashCommandRegistryLogger() {
		return SLASH_COMMAND_REGISTRY_LOGGER;
	}
	public static Logger getMysqlServiceLogger() {
		return MYSQL_SERVICE_LOGGER;
	}
	public static Logger getPugliaSaliMineraliLogger() {
		return PUGLIA_SALI_MINERALI_LOGGER;
	}

}