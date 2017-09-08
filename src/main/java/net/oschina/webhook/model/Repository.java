package net.oschina.webhook.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Repository {
	private String project_id;
	private String ssh_url;
	private String https_url;
	private String git_url;
	private String name;
	private String description;
	private String web_url;
	private User owner;

	
	private String url;
	
	
	
	
	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getProject_id() {
		return project_id;
	}



	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}



	public String getSsh_url() {
		return ssh_url;
	}



	public void setSsh_url(String ssh_url) {
		this.ssh_url = ssh_url;
	}



	public String getHttps_url() {
		return https_url;
	}



	public void setHttps_url(String https_url) {
		this.https_url = https_url;
	}



	public String getGit_url() {
		return git_url;
	}



	public void setGit_url(String git_url) {
		this.git_url = git_url;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getWeb_url() {
		return web_url;
	}



	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}



	public User getOwner() {
		return owner;
	}



	public void setOwner(User owner) {
		this.owner = owner;
	}



	public String projectPath() {
		Pattern pattern = Pattern.compile("https?://[^/]+/(?:u|t)/([^/]+)/p/([^/]+).*");
		Matcher matcher = pattern.matcher(web_url);
		if (matcher.matches()) {
			return String.format("%s/%s", matcher.group(1), matcher.group(2));
		}
		return "";
	}
}
