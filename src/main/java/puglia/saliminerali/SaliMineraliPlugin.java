package puglia.saliminerali;

import org.bukkit.plugin.java.JavaPlugin;
import puglia.saliminerali.listeners.PlayerChatListener;
import puglia.saliminerali.listeners.PlayerJoinLeaveListener;
import puglia.saliminerali_discord.services.ClientService;
import puglia.saliminerali_discord.services.IClientService;

import javax.security.auth.login.LoginException;

public final class SaliMineraliPlugin extends JavaPlugin {

	private static SaliMineraliPlugin instance;
	private static IClientService clientService;

	/*
	TODO: Minecraft-Side
	 Chat condivisa su discord [v]
	 Advancement condivisi su discord [v]
	 Entrate/Uscite condivise su discord [v]
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
		this.getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
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