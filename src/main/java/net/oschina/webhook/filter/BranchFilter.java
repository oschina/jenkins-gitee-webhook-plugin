package net.oschina.webhook.filter;

/**
 * @author Robin Müller
 */
public interface BranchFilter {

    boolean isBranchAllowed(String branchName);
}
