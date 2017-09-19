package net.oschina.webhook.cause;

import static com.google.common.base.Preconditions.checkNotNull;

import hudson.triggers.*;

public class OschinaWebHookCause extends SCMTrigger.SCMTriggerCause {

	private final CauseData data;

    public OschinaWebHookCause(CauseData data) {
        super("");
        this.data = checkNotNull(data, "data must not be null");
    }

    public CauseData getData() {
        return data;
    }

    @Override
    public String getShortDescription() {
        return data.getShortDescription();
    }
}
