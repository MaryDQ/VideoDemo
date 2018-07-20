package com.microsys.videodemo.ui.contract;

import com.microsys.videodemo.base.mvp.IPresenter;
import com.microsys.videodemo.base.mvp.IView;
import com.microsys.videodemo.ui.bean.video_info_beans.SimpleVideoInfoBean;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoID;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;

import java.util.ArrayList;
import java.util.List;

public interface GetAllLocationContract {
    interface View extends IView {
        void getLocationInfoSucceed(List<SimpleVideoInfoBean> list);
        void getVideoByIdsSucceed(ArrayList<VideoInfoBean> list);
    }

    interface Presenter<T> extends IPresenter <T>{
        void getAllLocationSimpleInfo();
        void getVideoByIds(ArrayList<VideoID> list);
    }
}
