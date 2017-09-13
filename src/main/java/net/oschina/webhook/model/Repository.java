package net.oschina.webhook.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Repository {
	private String name;
	private String url;
	private String description;
	private String homepage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String projectPath() {
		Pattern pattern = Pattern.compile("https?://[^/]+/(?:u|t)/([^/]+)/p/([^/]+).*");
		Matcher matcher = pattern.matcher(url);
		if (matcher.matches()) {
			return String.format("%s/%s", matcher.group(1), matcher.group(2));
		}
		return "";
	}
}
