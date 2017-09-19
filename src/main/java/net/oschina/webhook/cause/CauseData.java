package net.oschina.webhook.cause;

import org.apache.commons.lang.StringUtils;



public class CauseData {

	private ActionType actionType;

	private String token;
	private String userGK;
	private String userName;
	private String userUrl;

	private String projectPath;
	private String ref;
	private String before;
	private String after;
	private String repoUrl;
	private String commitId;

	private Integer mergeRequestId;
	private Integer mergeRequestIid;
	private String mergeRequestTitle;
	private String mergeRequestUrl;
	private String mergeRequestBody;

	private String sourceProjectPath;
	private String sourceBranch;
	private String sourceRepoUrl;
	private String sourceUser;
	private String targetBranch;

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserGK() {
		return userGK;
	}

	public void setUserGK(String userGK) {
		this.userGK = userGK;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserUrl() {
		return userUrl;
	}

	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public Integer getMergeRequestId() {
		return mergeRequestId;
	}

	public void setMergeRequestId(Integer mergeRequestId) {
		this.mergeRequestId = mergeRequestId;
	}

	public Integer getMergeRequestIid() {
		return mergeRequestIid;
	}

	public void setMergeRequestIid(Integer mergeRequestIid) {
		this.mergeRequestIid = mergeRequestIid;
	}

	public String getMergeRequestTitle() {
		return mergeRequestTitle;
	}

	public void setMergeRequestTitle(String mergeRequestTitle) {
		this.mergeRequestTitle = mergeRequestTitle;
	}

	public String getMergeRequestUrl() {
		return mergeRequestUrl;
	}

	public void setMergeRequestUrl(String mergeRequestUrl) {
		this.mergeRequestUrl = mergeRequestUrl;
	}

	public String getMergeRequestBody() {
		return mergeRequestBody;
	}

	public void setMergeRequestBody(String mergeRequestBody) {
		this.mergeRequestBody = mergeRequestBody;
	}

	public String getSourceProjectPath() {
		return sourceProjectPath;
	}

	public void setSourceProjectPath(String sourceProjectPath) {
		this.sourceProjectPath = sourceProjectPath;
	}

	public String getSourceBranch() {
		return sourceBranch;
	}

	public void setSourceBranch(String sourceBranch) {
		this.sourceBranch = sourceBranch;
	}

	public String getSourceRepoUrl() {
		return sourceRepoUrl;
	}

	public void setSourceRepoUrl(String sourceRepoUrl) {
		this.sourceRepoUrl = sourceRepoUrl;
	}

	public String getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(String sourceUser) {
		this.sourceUser = sourceUser;
	}

	public String getTargetBranch() {
		return targetBranch;
	}

	public void setTargetBranch(String targetBranch) {
		this.targetBranch = targetBranch;
	}

	String getShortDescription() {
		return actionType.getShortDescription(this);
	}

	public enum ActionType {
		PUSH {
			@Override
			String getShortDescription(CauseData data) {
				String pushedBy = data.getUserName();
				if (pushedBy == null) {
					return "Started by Oschina push";
				} else {
					String s="Started by Oschina push by %s";
					return String.format(s, pushedBy);
				}
			}
		},
		MR {
			@Override
			String getShortDescription(CauseData data) {
				String user = StringUtils.isEmpty(data.getUserName()) ? "Unknown" : data.getUserName();
				String s ="Started by %s's Merge Request #%d: %s(%s =&gt; %s) at Oschina";
				return String.format(s, user, data.getMergeRequestIid(),
						data.getMergeRequestTitle(), data.getSourceBranch(), data.getTargetBranch());
			}
		},
		TAG{
			@Override
			String getShortDescription(CauseData data) {
				String user = StringUtils.isEmpty(data.getUserName()) ? "Unknown" : data.getUserName();
				String s ="Started by %s's TAG #%d: %s(%s =&gt; %s) at Oschina";
				return String.format(s, user, data.getMergeRequestIid(),
						data.getMergeRequestTitle(), data.getSourceBranch(), data.getTargetBranch());
			}
		},
		NOTE{
			@Override
			String getShortDescription(CauseData data) {
				String user = StringUtils.isEmpty(data.getUserName()) ? "Unknown" : data.getUserName();
				String s ="Started by %s's NOTE #%d: %s(%s =&gt; %s) at Oschina";
				return String.format(s, user, data.getMergeRequestIid(),
						data.getMergeRequestTitle(), data.getSourceBranch(), data.getTargetBranch());
			}
		},
		ISSUE{
			@Override
			String getShortDescription(CauseData data) {
				String user = StringUtils.isEmpty(data.getUserName()) ? "Unknown" : data.getUserName();
				String s ="Started by %s's ISSUE #%d: %s(%s =&gt; %s) at Oschina";
				return String.format(s, user, data.getMergeRequestIid(),
						data.getMergeRequestTitle(), data.getSourceBranch(), data.getTargetBranch());
			}
		},
		PR {
			@Override
			String getShortDescription(CauseData data) {
				String user = StringUtils.isEmpty(data.getUserName()) ? "Unknown" : data.getUserName();
				String s = "Started by %s's Pull Request #%d: %s(%s:%s =&gt; %s) at Oschina";
				return String.format(s, user, data.getMergeRequestIid(),
						data.getMergeRequestTitle(), data.getSourceUser(), data.getSourceBranch(),
						data.getTargetBranch());
			}
		};

		abstract String getShortDescription(CauseData data);
	}
}
