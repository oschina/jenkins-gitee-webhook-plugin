package net.oschina.webhook;
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
import net.sf.json.JSONObject;
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

	private  String name;
	private  String webHookToken;
	private  boolean triggerOnPush;
    private  boolean triggerOnMergeRequest;
    private  BranchFilterType branchFilterType;
	private  TriggerHandler triggerHandler;
	private  boolean addResultNote;
	
	
	@DataBoundConstructor
    public OschinaPushTrigger(String webHookToken,  boolean triggerOnMergeRequest, 
                             boolean triggerOnPush,
                             BranchFilterType branchFilterType) {
        this.webHookToken = webHookToken;
        this.triggerOnPush = triggerOnPush;
        this.triggerOnMergeRequest = triggerOnMergeRequest;
        this.branchFilterType = branchFilterType;
        initializeTriggerHandler();
    }
	
	
	public TriggerHandler getTriggerHandler() {
		return triggerHandler;
	}


	public boolean isAddResultNote() {
		return addResultNote;
	}



	public String getWebHookToken() {
		return webHookToken;
	}


	public boolean isTriggerOnPush() {
		return triggerOnPush;
	}

	public boolean isTriggerOnMergeRequest() {
		return triggerOnMergeRequest;
	}


	public BranchFilterType getBranchFilterType() {
		return branchFilterType;
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
		
		BranchFilterConfig branchFilterConfig = new BranchFilterConfig(branchFilterType);
        
		this.triggerHandler = new TriggerHandler(true, true,
				BranchFilterFactory.newBranchFilter(branchFilterConfig));
	}

	public void onPost(WebHook webHook, String event) {
		System.out.println(webHookToken);
		System.out.println( webHook.getPassword());
		if (StringUtils.isEmpty(webHookToken) || StringUtils.equals(webHookToken, webHook.getPassword())) {
			triggerHandler.handle(job, webHook, event);
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
					return "Gitee WebHook插件 (在码云项目的WebHooks使用这个URL地址："+retrieveProjectUrl(project).toString()+")";
				} catch (IllegalStateException e) {
				}
			}
			return "";
		}

		private StringBuilder retrieveProjectUrl(Job<?, ?> project) {
			return new StringBuilder().append(Jenkins.getInstance().getRootUrl()).append(OschinaWebHook.WEBHOOK_URL)
					.append(retrieveParentUrl(project)).append('/').append(Util.rawEncode(project.getName()));
		}
		
		
		@Override
		public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
			save();
			return super.configure(req, formData);
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
