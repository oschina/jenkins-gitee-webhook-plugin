package net.oschina.webhook.filter;


public class BranchFilterFactory {

	private BranchFilterFactory() { }

    public static BranchFilter newBranchFilter(BranchFilterConfig config) {
//        switch (config.getType()) {
//            case NameBasedFilter:
//                return new NameBasedFilter(config.getIncludeBranchesSpec(), config.getExcludeBranchesSpec());
//            case RegexBasedFilter:
//                return new RegexBasedFilter(config.getTargetBranchRegex());
//            default:
//                return new AllBranchesFilter();
//        }
    	 return new AllBranchesFilter();
    }
}
