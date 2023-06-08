package puglia.saliminerali.services;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import puglia.saliminerali.PugliaSaliMinerali;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
		this.executorService.scheduleAtFixedRate(() -> {
			Activity activity = activityListOrder.get(index);
			client.getPresence()
					.setActivity(activity);
			if(this.index == activityListOrder.size()-1) {
				this.executorService.shutdown();
			}
			index++;
		}, 10, 15, TimeUnit.SECONDS);
	}
}
