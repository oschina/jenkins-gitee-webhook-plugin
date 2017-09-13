package net.oschina.webhook.model;

public class MergeRequest {

	private String iid;
	private String title;
	private String body;
	private String state;
	private String merge_status;
	private String source_branch;
	private String target_branch;
	private String hook_name;
	private String password;

	private String merge_commit_sha;
	
	private Repository target_repo;
	private Repository source_repo;

	
	public String getMerge_commit_sha() {
		return merge_commit_sha;
	}

	public void setMerge_commit_sha(String merge_commit_sha) {
		this.merge_commit_sha = merge_commit_sha;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMerge_status() {
		return merge_status;
	}

	public void setMerge_status(String merge_status) {
		this.merge_status = merge_status;
	}

	public String getSource_branch() {
		return source_branch;
	}

	public void setSource_branch(String source_branch) {
		this.source_branch = source_branch;
	}

	public String getTarget_branch() {
		return target_branch;
	}

	public void setTarget_branch(String target_branch) {
		this.target_branch = target_branch;
	}

	public String getHook_name() {
		return hook_name;
	}

	public void setHook_name(String hook_name) {
		this.hook_name = hook_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Repository getTarget_repo() {
		return target_repo;
	}

	public void setTarget_repo(Repository target_repo) {
		this.target_repo = target_repo;
	}

	public Repository getSource_repo() {
		return source_repo;
	}

	public void setSource_repo(Repository source_repo) {
		this.source_repo = source_repo;
	}

}
