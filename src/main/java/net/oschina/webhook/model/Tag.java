package net.oschina.webhook.model;

public class Tag {

	private String ref;
	private String before;
	private String after;

	private Repository repository;
	private Project project;

	private String hook_name;
	private String password;
	
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

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
