package com.microsys.videodemo.eventbus_events;

public class DismissDialogEvent {
    private boolean isRefresh = false;
    private String content;

    public DismissDialogEvent(boolean isRefresh, String content) {
        this.isRefresh = isRefresh;
        this.content = content;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
