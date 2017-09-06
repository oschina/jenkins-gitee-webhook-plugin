package net.oschina.webhook.model;
public class PullRequest extends MergeRequest {
    private Repository source_repository;

	public Repository getSource_repository() {
		return source_repository;
	}

	public void setSource_repository(Repository source_repository) {
		this.source_repository = source_repository;
	}
    
}
