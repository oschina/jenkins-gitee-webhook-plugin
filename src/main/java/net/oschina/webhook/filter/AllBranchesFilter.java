package net.oschina.webhook.filter;

/**
 * @author Robin Müller
 */
class AllBranchesFilter implements BranchFilter {
    @Override
    public boolean isBranchAllowed(String branchName) {
        return true;
    }
}
