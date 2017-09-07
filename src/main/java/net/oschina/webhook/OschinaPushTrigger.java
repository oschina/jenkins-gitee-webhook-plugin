package net.oschina.webhook;

import hudson.Extension;
import hudson.Util;
import hudson.util.FormValidation;
import jenkins.model.*;
import jenkins.triggers.SCMTriggerItem;
import net.oschina.webhook.filter.BranchFilterConfig;
import net.oschina.webhook.filter.BranchFilterFactory;
import net.oschina.webhook.filter.BranchFilterType;
import net.oschina.webhook.handler.TriggerHandler;
import net.oschina.webhook.model.WebHook;
import hudson.model.*;
import hudson.triggers.Trigger;
import hudson.triggers.TriggerDescriptor;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.Ancestor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.Stapler;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.ObjectStreamException;

public class OschinaPushTrigger extends Trigger<Job<?, ?>> {

	private final String name;
	private boolean ciSkip;
	private String webHookToken;
	private String apiToken;

	private boolean triggerOnPush;
    private boolean triggerOnMergeRequest;
    private String mergeRequestTriggerAction;
    private BranchFilterType branchFilterType;
    private String includeBranchesSpec;
    private String excludeBranchesSpec;
    private String targetBranchRegex;
	private transient TriggerHandler triggerHandler;

	@DataBoundConstructor
	public OschinaPushTrigger(String name) {
		this.name = name;
		initializeTriggerHandler();
	}

	public String getName() {
		return name;
	}

	@Override
    protected Object readResolve() throws ObjectStreamException {
        initializeTriggerHandler();
        return super.readResolve();
    }
	
	private void initializeTriggerHandler() {
		
		BranchFilterConfig branchFilterConfig = new BranchFilterConfig(
                branchFilterType, includeBranchesSpec, excludeBranchesSpec, targetBranchRegex);
        
		this.triggerHandler = new TriggerHandler(this.triggerOnPush, this.triggerOnMergeRequest,
				mergeRequestTriggerAction, BranchFilterFactory.newBranchFilter(branchFilterConfig));
	}

	public void onPost(WebHook webHook, String event) {
		if (StringUtils.isEmpty(webHookToken) || StringUtils.equals(webHookToken, webHook.getToken())) {
			System.out.println("OschinaPushTrigger onPost.");
			triggerHandler.handle(job, webHook, event, ciSkip);
		}
	}

	public static OschinaPushTrigger getFromJob(Job<?, ?> job) {
		OschinaPushTrigger trigger = null;
		if (job instanceof ParameterizedJobMixIn.ParameterizedJob) {
			ParameterizedJobMixIn.ParameterizedJob p = (ParameterizedJobMixIn.ParameterizedJob) job;
			for (Object t : p.getTriggers().values()) {
				if (t instanceof OschinaPushTrigger) {
					trigger = (OschinaPushTrigger) t;
				}
			}
		}
		return trigger;
	}

	@Extension
	public static final class DescriptorImpl extends TriggerDescriptor {

		public DescriptorImpl() {
			load();
		}

		public FormValidation doCheckName(@QueryParameter String value) throws IOException, ServletException {
			if (value.length() == 0)
				return FormValidation.error("Please set a name");
			if (value.length() < 4)
				return FormValidation.warning("Isn't the name too short?");
			return FormValidation.ok();
		}

		@Override
		public String getDisplayName() {
			Job<?, ?> project = retrieveCurrentJob();
			if (project != null) {
				try {
					return retrieveProjectUrl(project).toString();
				} catch (IllegalStateException e) {
					// nothing to do
				}
			}
			return "test12345";
		}

		private StringBuilder retrieveProjectUrl(Job<?, ?> project) {
			return new StringBuilder().append(Jenkins.getInstance().getRootUrl()).append(OschinaWebHook.WEBHOOK_URL)
					.append(retrieveParentUrl(project)).append('/').append(Util.rawEncode(project.getName()));
		}

		private StringBuilder retrieveParentUrl(Item item) {
			if (item.getParent() instanceof Item) {
				Item parent = (Item) item.getParent();
				return retrieveParentUrl(parent).append('/').append(Util.rawEncode(parent.getName()));
			} else {
				return new StringBuilder();
			}
		}

		private Job<?, ?> retrieveCurrentJob() {
			StaplerRequest request = Stapler.getCurrentRequest();
			if (request != null) {
				Ancestor ancestor = request.findAncestor(Job.class);
				return ancestor == null ? null : (Job<?, ?>) ancestor.getObject();
			}
			return null;
		}

		@Override
		public boolean isApplicable(Item item) {
			return item instanceof Job && SCMTriggerItem.SCMTriggerItems.asSCMTriggerItem(item) != null
					&& item instanceof ParameterizedJobMixIn.ParameterizedJob;
		}
	}
}
