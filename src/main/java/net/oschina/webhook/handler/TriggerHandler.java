package net.oschina.webhook.handler;
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
import hudson.model.*;
import net.oschina.webhook.*;
import net.oschina.webhook.cause.CauseData;
import net.oschina.webhook.cause.OschinaWebHookCause;
import net.oschina.webhook.filter.BranchFilter;
import net.oschina.webhook.model.Commit;
import net.oschina.webhook.model.WebHook;
import static net.oschina.webhook.cause.CauseData.ActionType;
import static org.eclipse.jgit.lib.Repository.shortenRefName;

import hudson.plugins.git.RevisionParameterAction;
import jenkins.model.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.transport.URIish;

public class TriggerHandler {

	private static final String PULL_REQUEST_EVENT = "pull_request";

	private static final String MERGE_REQUEST_EVENT = "Merge Request Hook";
	private static final String PUSH_REQUEST_EVENT = "Push Hook";
	private static final String ISSUE_REQUEST_EVENT = "Issue Hook";
	private static final String NOTE_REQUEST_EVENT = "Note Hook";
	private static final String PUSH_TAG_REQUEST_EVENT = "Tag Push Hook";

	private static final String NO_COMMIT = "0000000000000000000000000000000000000000";
	private static final String CI_SKIP = "[ci-skip]";

	private boolean triggerOnPush;
	private boolean triggerOnMergeRequest;
	private boolean triggerOnTagRequest=true;
	private boolean triggerOnNoteRequest=true;
	private boolean triggerOnIssueRequest=true;
	private String mergeRequestTriggerAction;
	private BranchFilter branchFilter;

	public TriggerHandler(boolean triggerOnPush, boolean triggerOnMergeRequest,
			BranchFilter branchFilter) {
		this.triggerOnPush = triggerOnPush;
		this.triggerOnMergeRequest = triggerOnMergeRequest;
		this.branchFilter = branchFilter;
	}

	public void handle(Job<?, ?> job, WebHook hook, String event) {
		if (job instanceof AbstractProject<?, ?>) {
			AbstractProject<?, ?> project = (AbstractProject<?, ?>) job;
			final OschinaPushTrigger trigger = project.getTrigger(OschinaPushTrigger.class);
			System.out.println(trigger);
			System.out.println(event);
			if (trigger != null) {
				boolean shouldTrigger = false;
				ActionType actionType = null;
				String branch = null;
				switch (event) {
				case PUSH_TAG_REQUEST_EVENT:
					break;
				case NOTE_REQUEST_EVENT:
					break;
				case ISSUE_REQUEST_EVENT:
					break;
				case MERGE_REQUEST_EVENT:
					branch = hook.getTarget_branch();
					String commit = hook.getMerge_commit_sha();
					if(commit==null){
						return;
					}
					shouldTrigger = triggerOnMergeRequest;
					actionType = ActionType.MR;
					break;
				case PUSH_REQUEST_EVENT:
					if (isNoRemoveBranchPush(hook)) {
						shouldTrigger = triggerOnPush;
						actionType = ActionType.PUSH;
						branch = shortenRefName(hook.getRef());
					}
					break;
				default:
					break;
				}
				if (shouldTrigger) {
					scheduleBuild(job, createActions(job, hook, actionType));
				}
			}
		}
	}

	private void scheduleBuild(Job<?, ?> job, Action[] actions) {
		int projectBuildDelay = 0;
		if (job instanceof ParameterizedJobMixIn.ParameterizedJob) {
			ParameterizedJobMixIn.ParameterizedJob abstractProject = (ParameterizedJobMixIn.ParameterizedJob) job;
			if (abstractProject.getQuietPeriod() > projectBuildDelay) {
				projectBuildDelay = abstractProject.getQuietPeriod();
			}
		}
		asParameterizedJobMixIn(job).scheduleBuild2(projectBuildDelay, actions);
	}

	private static <T extends Job> ParameterizedJobMixIn asParameterizedJobMixIn(final T job) {
		return new ParameterizedJobMixIn() {
			@Override
			protected Job asJob() {
				return job;
			}
		};
	}

	private String shortenRef(String ref) {
		return ref == null ? null : shortenRefName(ref);
	}

	private CauseData buildCauseData(WebHook hook, ActionType actionType) {
		CauseData data = new CauseData();
		data.setActionType(actionType);
		data.setToken(hook.getPassword());
		data.setRef(hook.getRef());
		data.setBefore(hook.getBefore());
		data.setAfter(hook.getAfter());
		data.setCommitId(hook.getAfter());
		if(hook.getHook_name().equals("merge_request_hooks")){
			System.out.println("merge_request_hooks:"+hook.getAfter());
			data.setRepoUrl(hook.getSource_repo().getProject().getGit_ssh_url());
		}
		return data;
	}

	private RevisionParameterAction createRevisionParameter(WebHook hook, ActionType actionType) {
		return new RevisionParameterAction(retrieveRevisionToBuild(hook, actionType), createUrIish(hook));
	}

	private URIish createUrIish(WebHook hook) {
		try {
			if (hook.getRepository() != null) {
				return new URIish(hook.getRepository().getUrl());
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String retrieveRevisionToBuild(WebHook hook, ActionType actionType) {
		String revision = null;
		System.out.println("retrieveRevisionToBuild:" + actionType);
		switch (actionType) {
		case PUSH:
			if ((hook.getCommits() == null || hook.getCommits().isEmpty())) {
				if (isNewBranchPush(hook)) {
					revision = hook.getAfter();
				}
			} else {
				List<Commit> commits = hook.getCommits();
				revision = commits.get(commits.size() - 1).getId();
			}
			break;
		case MR:
			 String commit_sha = hook.getMerge_commit_sha();
			 System.out.println("commit_sha:"+commit_sha);
			 revision = commit_sha;
			break;
		default:
			break;
		}
		if (StringUtils.isEmpty(revision)) {
			throw new IllegalStateException("No revision to build");
		}
		return revision;
	}

	private boolean isTriggerAction(String action) {
		return StringUtils.isEmpty(mergeRequestTriggerAction)
				|| StringUtils.contains(mergeRequestTriggerAction, action);
	}

	private boolean isNewBranchPush(WebHook hook) {
		return hook.getBefore() != null && hook.getBefore().equals(NO_COMMIT);
	}

	private Action[] createActions(Job<?, ?> job, WebHook hook, ActionType actionType) {
		List<Action> actions = new ArrayList<>();
		actions.add(new CauseAction(new OschinaWebHookCause(buildCauseData(hook, actionType))));
		try {
			actions.add(createRevisionParameter(hook, actionType));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return actions.toArray(new Action[actions.size()]);
	}

	private boolean isNoRemoveBranchPush(WebHook hook) {
		return hook.getAfter() != null && !hook.getAfter().equals(NO_COMMIT);
	}
}
