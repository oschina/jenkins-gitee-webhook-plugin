package net.oschina.webhook.listener;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.BuildStepListener;
import hudson.tasks.BuildStep;

@Extension
public class BuildResultListener extends BuildStepListener {

	@Override
	public void started(AbstractBuild build, BuildStep bs, BuildListener listener) {
		System.out.println("BuildResultListener started.");
	}

	@Override
	public void finished(AbstractBuild build, BuildStep bs, BuildListener listener, boolean canContinue) {
		System.out.println("BuildResultListener finished.");
	}

}
