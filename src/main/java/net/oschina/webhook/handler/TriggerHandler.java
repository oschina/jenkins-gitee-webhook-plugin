package net.oschina.webhook.handler;

import hudson.model.*;
import net.oschina.webhook.*;
import net.oschina.webhook.filter.BranchFilter;
import net.oschina.webhook.model.WebHook;
import static net.oschina.webhook.cause.CauseData.ActionType;

public class TriggerHandler {

	private static final String PUSH_EVENT = "push";
	private static final String PULL_REQUEST_EVENT = "pull_request";
	private static final String MERGE_REQUEST_EVENT = "merge_request";

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
                default:
                    break;
				}
			}
		}
	}
}
