package com.microsys.videodemo.ui.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsys.videodemo.api.Api;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;
import com.microsys.videodemo.ui.contract.SearchVideoContract;
import com.microsys.videodemo.utils.StringUtil;
import com.microsys.videodemo.utils.ThreadUtils;
import com.microsys.videodemo.utils.http.RubishHttpUtils;

import java.util.ArrayList;

import javax.inject.Inject;

public class SearchVideoPresenter extends RxBusinessPresenter<SearchVideoContract.View> implements
        SearchVideoContract.Presenter<SearchVideoContract.View> {

    Api api;

    @Inject
    public SearchVideoPresenter(Api api) {
        this.api = api;
    }

    @Override
    public void searchVideoByKeywords(final String keyWords, final int begin, final int limit) {

        ThreadUtils.doSingleThread(new Runnable() {
            @Override
            public void run() {
                String url = Api.BASE_URL + "SearchVideo";
                String result = "";
                try {
                    result = new RubishHttpUtils().getPostData(url, "VideoName", keyWords, "begin", begin + "", "limit", "" + limit);
                    if (StringUtil.isNotEmpty(result)) {
                        ArrayList<VideoInfoBean> videoInfoBeansList = new Gson().fromJson(result, new TypeToken<ArrayList<VideoInfoBean>>() {
                        }.getType());
                        view.searchVideoSucceed(videoInfoBeansList);
                    } else {
                        view.complete();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
