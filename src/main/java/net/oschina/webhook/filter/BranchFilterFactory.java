package net.oschina.webhook.filter;


public class BranchFilterFactory {

	private BranchFilterFactory() { }

    public static BranchFilter newBranchFilter(BranchFilterConfig config) {
    	 return new AllBranchesFilter();
    }
}
