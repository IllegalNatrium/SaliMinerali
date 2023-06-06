package puglia.saliminerali;

import org.bukkit.plugin.java.JavaPlugin;
import puglia.saliminerali.listeners.PlayerJoinLeaveListener;
import puglia.saliminerali.services.ClientService;
import puglia.saliminerali.services.IClientService;

import javax.security.auth.login.LoginException;

public final class SaliMineraliPlugin extends JavaPlugin {

	private static SaliMineraliPlugin instance;
	private static IClientService clientService;

	/*
	TODO:
	 Chat condivisa su discord
	 Advancement condivisi su discord
	 Scoperta nuovi recive condivisi su discord
	 Entrate/Uscite condivise su discord
	*/

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		clientService = new ClientService();
		try {
			clientService.initializeClientService(getConfig().getString("TOKEN"));
		} catch (LoginException | InterruptedException ignored) {}

		this.getServer().getPluginManager().registerEvents(new PlayerJoinLeaveListener(), this);
	}

	@Override
	public void onDisable() {
		clientService.disposeClientService();
	}

	public static SaliMineraliPlugin getInstance() {
		return instance;
	}

	public static IClientService getClientService() {
		return clientService;
	}

}