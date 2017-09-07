package net.oschina.webhook.filter;


public class BranchFilterConfig {

	private BranchFilterType type;
    private String includeBranchesSpec;
    private String excludeBranchesSpec;
    private String targetBranchRegex;
    
    public BranchFilterConfig(BranchFilterType type,String includeBranchesSpec,
    		String excludeBranchesSpec,String targetBranchRegex){
    	this.type = type;
    	this.includeBranchesSpec = includeBranchesSpec;
    	this.excludeBranchesSpec = excludeBranchesSpec;
    	this.targetBranchRegex = targetBranchRegex;
    }
    
	public BranchFilterType getType() {
		return type;
	}
	public void setType(BranchFilterType type) {
		this.type = type;
	}
	public String getIncludeBranchesSpec() {
		return includeBranchesSpec;
	}
	public void setIncludeBranchesSpec(String includeBranchesSpec) {
		this.includeBranchesSpec = includeBranchesSpec;
	}
	public String getExcludeBranchesSpec() {
		return excludeBranchesSpec;
	}
	public void setExcludeBranchesSpec(String excludeBranchesSpec) {
		this.excludeBranchesSpec = excludeBranchesSpec;
	}
	public String getTargetBranchRegex() {
		return targetBranchRegex;
	}
	public void setTargetBranchRegex(String targetBranchRegex) {
		this.targetBranchRegex = targetBranchRegex;
	}
    
}
