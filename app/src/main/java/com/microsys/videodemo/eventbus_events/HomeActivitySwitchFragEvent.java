package com.microsys.videodemo.eventbus_events;

public class HomeActivitySwitchFragEvent {
    private boolean refresh = false;
    private int item = 0;

    public HomeActivitySwitchFragEvent(boolean refresh, int item) {
        this.refresh = refresh;
        this.item = item;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }
}
