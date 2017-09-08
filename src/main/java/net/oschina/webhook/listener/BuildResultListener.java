package net.oschina.webhook.listener;

import java.io.IOException;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.BuildStepListener;
import hudson.model.Result;
import hudson.tasks.BuildStep;
import net.oschina.webhook.cause.OschinaWebHookCause;

@Extension
public class BuildResultListener extends BuildStepListener {

	@Override
	public void started(AbstractBuild build, BuildStep bs, BuildListener listener) {
	}

	@Override
	public void finished(AbstractBuild build, BuildStep bs, BuildListener listener, boolean canContinue) {
		if (build.getCause(OschinaWebHookCause.class) != null
                && build.getResult() == Result.FAILURE
                && isCommitAmbiguous(build)) {
            build.setResult(Result.NOT_BUILT);
        }
	}
	
	private boolean isCommitAmbiguous(AbstractBuild build) {
        try {
            for (Object log : build.getLog(500)) {
                String str = log.toString();
                if (str.contains("stderr: fatal: ambiguous argument")
                        && str.contains("unknown revision or path not in the working tree")) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

}
