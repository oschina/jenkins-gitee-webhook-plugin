package net.oschina.webhook.model;


public class MergeRequest {
    private Integer id;
    private String title;
    private String body;
    private String merge_commit_sha;
    private String status;
    private String action;
    private Integer number;
    private String target_branch;
    private String source_branch;
    private String web_url;
    private User user;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getMerge_commit_sha() {
		return merge_commit_sha;
	}
	public void setMerge_commit_sha(String merge_commit_sha) {
		this.merge_commit_sha = merge_commit_sha;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getTarget_branch() {
		return target_branch;
	}
	public void setTarget_branch(String target_branch) {
		this.target_branch = target_branch;
	}
	public String getSource_branch() {
		return source_branch;
	}
	public void setSource_branch(String source_branch) {
		this.source_branch = source_branch;
	}
	public String getWeb_url() {
		return web_url;
	}
	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
