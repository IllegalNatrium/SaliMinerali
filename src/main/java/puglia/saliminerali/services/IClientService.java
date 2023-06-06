package puglia.saliminerali.services;

import net.dv8tion.jda.api.JDA;

import javax.security.auth.login.LoginException;

public interface IClientService {

	void initializeClientService(String token) throws LoginException, InterruptedException;
	JDA getClientService();
	void disposeClientService();

}
