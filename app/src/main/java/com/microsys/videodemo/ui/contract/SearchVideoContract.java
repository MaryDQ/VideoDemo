package com.microsys.videodemo.ui.contract;

import com.microsys.videodemo.base.mvp.IPresenter;
import com.microsys.videodemo.base.mvp.IView;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;

import java.util.ArrayList;

public interface SearchVideoContract {
    interface View extends IView {
        void searchVideoSucceed(ArrayList<VideoInfoBean> list);
    }

    interface Presenter<T> extends IPresenter<T> {
        void searchVideoByKeywords(String keyWords,int begin,int limit);
    }
}
