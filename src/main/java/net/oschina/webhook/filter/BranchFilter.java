package net.oschina.webhook.filter;


public interface BranchFilter {

    boolean isBranchAllowed(String branchName);
}
