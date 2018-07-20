package com.microsys.videodemo.eventbus_events;

public class HomeActivityRefreshEvent {
    private boolean refresh;
    private String content;

    public HomeActivityRefreshEvent(boolean refresh, String content) {
        this.refresh = refresh;
        this.content = content;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
