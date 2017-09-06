package net.oschina.webhook.model;

import java.util.List;

public class WebHook {
    private String ref;
    private List<Commit> commits;
    private String before;
    private String after;
    private Repository repository;
    private String event;
    private String token;
    private MergeRequest merge_request;
    private PullRequest pull_request;
    private User user;
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public List<Commit> getCommits() {
		return commits;
	}
	public void setCommits(List<Commit> commits) {
		this.commits = commits;
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
	public Repository getRepository() {
		return repository;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public MergeRequest getMerge_request() {
		return merge_request;
	}
	public void setMerge_request(MergeRequest merge_request) {
		this.merge_request = merge_request;
	}
	public PullRequest getPull_request() {
		return pull_request;
	}
	public void setPull_request(PullRequest pull_request) {
		this.pull_request = pull_request;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
    
}
