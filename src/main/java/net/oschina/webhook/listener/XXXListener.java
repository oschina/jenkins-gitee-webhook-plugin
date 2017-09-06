package net.oschina.webhook.listener;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;

@Extension
public class XXXListener extends RunListener<Run<?, ?>> {

	@Override
	public void onCompleted(Run<?, ?> r, TaskListener listener) {
		
		System.out.println("XXXListener onCompleted.");
		super.onCompleted(r, listener);
	}

	@Override
	public void onStarted(Run<?, ?> r, TaskListener listener) {
		System.out.println("XXXListener onStarted.");
		super.onStarted(r, listener);
	}

}
