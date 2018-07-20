package com.microsys.videodemo.ui.contract;

import com.microsys.videodemo.base.mvp.IPresenter;
import com.microsys.videodemo.base.mvp.IView;
import com.microsys.videodemo.ui.bean.video_info_beans.GroupOrVideoBean;

import java.util.List;

public interface NewCategoryListContract {
    interface View extends IView{
        void newCategoryListSucceed(List<GroupOrVideoBean.GroupsBean> videoGroupBeanList, List<GroupOrVideoBean.VideosBean> videoInfoBeanList,String beforeGroupId,String beforeGroupName);
    }

    interface Presenter<T> extends IPresenter<T> {
        void getNewCategoryList(String ids,String groupName,int begin ,int limit);
    }
}
