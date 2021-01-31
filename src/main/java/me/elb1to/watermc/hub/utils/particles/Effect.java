package me.elb1to.watermc.hub.utils.particles;

import me.elb1to.watermc.hub.utils.extra.ServerUtils;
import me.elb1to.watermc.hub.utils.particles.data.EffectLocation;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Elb1to
 * Project: FrozedHubDeluxe
 * Date: 1/16/2021 @ 4:00 PM
 */
public abstract class Effect {

	protected long period = 1;
	protected EffectLocation effectLocation;
	private int ticksToRun = 20, ticks = 0;
	private BukkitTask task;
	private EffectLocation targetLocation;
	private boolean running = false;
	private Callback<Effect> callback;

	public Effect(int ticks, EffectLocation effectLocation) {
		ticksToRun = ticks;
		this.effectLocation = effectLocation;
	}

	public Effect(int ticks, EffectLocation effectLocation, long delay) {
		ticksToRun = ticks;
		this.effectLocation = effectLocation;
		period = delay;
	}

	public void start() {
		onStart();
		running = true;
		task = ServerUtils.runSyncTimer(() -> {
			runEffect();
			update();
		}, 5, period);
	}

	public void stop() {
		running = false;
		task.cancel();

		if (callback != null) {
			callback.run(this);
		}

		onStop();
	}

	public void onStart() {

	}

	public void onStop() {

	}

	private void update() {
		if (++ticks == ticksToRun) {
			task.cancel();
		}
	}

	public boolean isRunning() {
		return running;
	}

	public abstract void runEffect();

	public EffectLocation getTargetLocation() {
		return targetLocation;
	}

	public void setTargetLocation(EffectLocation effectLocation) {
		targetLocation = effectLocation;
	}

	public EffectLocation getEffectLocation() {
		return effectLocation;
	}

	public void setCallback(Callback<Effect> callback) {
		this.callback = callback;
	}
}
