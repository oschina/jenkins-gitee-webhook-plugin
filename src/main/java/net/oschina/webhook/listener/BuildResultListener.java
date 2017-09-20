/**
 * Copyright (C) 2017 ChengSong Hu <644340980@qq.com>
 * Copyright (C) 2016 Shuanglei Tao <tsl0922@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
