package com.microsys.videodemo.ui.contract;

import com.microsys.videodemo.base.mvp.IPresenter;
import com.microsys.videodemo.base.mvp.IView;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;

import java.util.List;

public interface GetVideoContract {

    interface View extends IView{
        void getVideosSucceed(List<VideoInfoBean> list);
    }

    interface  Presenter<T> extends IPresenter<T>{
        void getVideos(String groupId,int begin,int limit);
    }
}
