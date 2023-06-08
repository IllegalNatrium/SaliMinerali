package puglia.saliminerali.services;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* Needs a Custom Task Manager to be implemented, Not in use*/
public class PresenceService extends ListenerAdapter implements IPresenceService {

	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

	@Override
	public void onReady(@NotNull ReadyEvent event) {
		initializePresenceService(event.getJDA());
	}

	private int index = 0;

	@Override
	public void initializePresenceService(JDA client) {
		List<Activity> activityListOrder = ClientService.activityListOrder;
		this.executorService.schedule(() -> {
			Activity activity = activityListOrder.get(index);
			client.getPresence()
					.setActivity(activity);
			index++;
		}, 2, TimeUnit.MINUTES);
	}
}
