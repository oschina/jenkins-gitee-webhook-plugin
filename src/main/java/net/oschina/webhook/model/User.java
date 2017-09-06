package net.oschina.webhook.model;
public class User {
    private String name;
    private String global_key;
    private String path;
    private String avatar;
    private String web_url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGlobal_key() {
		return global_key;
	}
	public void setGlobal_key(String global_key) {
		this.global_key = global_key;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getWeb_url() {
		return web_url;
	}
	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}
    
}
