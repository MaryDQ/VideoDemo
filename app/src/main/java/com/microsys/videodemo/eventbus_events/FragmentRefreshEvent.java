package com.microsys.videodemo.eventbus_events;

import com.microsys.videodemo.ui.bean.video_info_beans.GroupOrVideoBean;

import java.util.ArrayList;
import java.util.List;

public class FragmentRefreshEvent {
    public boolean refresh;
    public String content;
    public List<GroupOrVideoBean.GroupsBean> list;


    public FragmentRefreshEvent(boolean refresh, String content, List<GroupOrVideoBean.GroupsBean> list) {
        this.refresh = refresh;
        this.content = content;
        this.list = list;
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

    public List<GroupOrVideoBean.GroupsBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<GroupOrVideoBean.GroupsBean> list) {
        this.list = list;
    }
}
