package com.microsys.videodemo.ui.contract;

import com.microsys.videodemo.base.mvp.IPresenter;
import com.microsys.videodemo.base.mvp.IView;
import com.microsys.videodemo.ui.bean.video_info_beans.GroupOrVideoBean;

import java.util.List;

public interface GetRecommendListContract {

    interface View extends IView {
        void getRecommendListSucceed(List<GroupOrVideoBean.GroupsBean> groupsBeanList);
    }

    interface Presenter<T> extends IPresenter<T> {
        void getRecommendList();
    }

}
