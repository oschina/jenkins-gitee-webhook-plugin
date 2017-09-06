package net.oschina.webhook.model;
public class Commit {
    private Committer committer;
    private String web_url;
    private String short_message;
    private String sha;
	public Committer getCommitter() {
		return committer;
	}
	public void setCommitter(Committer committer) {
		this.committer = committer;
	}
	public String getWeb_url() {
		return web_url;
	}
	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}
	public String getShort_message() {
		return short_message;
	}
	public void setShort_message(String short_message) {
		this.short_message = short_message;
	}
	public String getSha() {
		return sha;
	}
	public void setSha(String sha) {
		this.sha = sha;
	}
}
