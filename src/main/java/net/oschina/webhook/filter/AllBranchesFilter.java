package net.oschina.webhook.filter;

class AllBranchesFilter implements BranchFilter {
    @Override
    public boolean isBranchAllowed(String branchName) {
        return true;
    }
}
