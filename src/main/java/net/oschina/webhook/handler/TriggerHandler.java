package net.oschina.webhook.handler;

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

	private static final String PUSH_EVENT = "push";
	private static final String PULL_REQUEST_EVENT = "pull_request";
	private static final String MERGE_REQUEST_EVENT = "merge_request";
	private static final String PUSHHOOK = "Push Hook";
	private static final String NO_COMMIT = "0000000000000000000000000000000000000000";
	private static final String CI_SKIP = "[ci-skip]";

	private boolean triggerOnPush;
    private boolean triggerOnMergeRequest;
    private String mergeRequestTriggerAction;
    private BranchFilter branchFilter;
    
	public TriggerHandler(boolean triggerOnPush, boolean triggerOnMergeRequest, String mergeRequestTriggerAction, BranchFilter branchFilter) {
        this.triggerOnPush = triggerOnPush;
        this.triggerOnMergeRequest = triggerOnMergeRequest;
        this.mergeRequestTriggerAction = mergeRequestTriggerAction;
        this.branchFilter = branchFilter;
    }
	
	
	
	
	public void handle(Job<?, ?> job, WebHook hook, String event, boolean ciSkip) {
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
				case PUSH_EVENT:
					System.out.println("PUSH_EVENT");
                    break;
                case MERGE_REQUEST_EVENT:
                	System.out.println("MERGE_REQUEST_EVENT");
                    break;
                case PULL_REQUEST_EVENT:
                	System.out.println("PULL_REQUEST_EVENT");
                    break;
                case PUSHHOOK:
                	if (isNoRemoveBranchPush(hook)) {
                		System.out.println("push");
                        shouldTrigger = triggerOnPush;
                        actionType = ActionType.PUSH;
                        branch = shortenRefName(hook.getRef());
                        System.out.println(actionType);
                        System.out.println(branch);
                    }
                	break;
                default:
                	System.out.println("event:"+event);
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
		System.out.println("buildCauseData:"+hook);
		CauseData data = new CauseData();
		data.setActionType(actionType);
        data.setToken(hook.getToken());
        if (hook.getUser() != null) {
            data.setUserGK(hook.getUser().getGlobal_key());
            data.setUserName(hook.getUser().getName());
            data.setUserUrl(hook.getUser().getWeb_url());
        }
        data.setRef(hook.getRef());
        data.setBefore(hook.getBefore());
        data.setAfter(hook.getAfter());
        data.setCommitId(hook.getAfter());
        data.setRepoUrl(hook.getRepository().getSsh_url());
        System.out.println("Repository："+hook.getRepository());
//        data.setProjectPath(hook.getRepository().projectPath());
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
		System.out.println("retrieveRevisionToBuild:"+actionType);
        switch (actionType) {
            case PUSH:
                
            	
            	
            	if ((hook.getCommits() == null || hook.getCommits().isEmpty())) {
            		System.out.println("1234");
            		if (isNewBranchPush(hook)) {
                        revision = hook.getAfter();
                    }
                } else {
                    List<Commit> commits = hook.getCommits();
                    System.out.println(commits.get(commits.size() - 1).getId());
                    revision = commits.get(commits.size() - 1).getId();
                }
                break;
            case MR:
                revision = hook.getMerge_request().getMerge_commit_sha();
                break;
            case PR:
                revision = hook.getPull_request().getMerge_commit_sha();
            default:
                break;
        }
        if (StringUtils.isEmpty(revision)) {
            throw new IllegalStateException("No revision to build");
        }
        return revision;
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
