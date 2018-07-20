package com.microsys.videodemo.ui.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsys.videodemo.api.Api;
import com.microsys.videodemo.ui.bean.video_info_beans.SimpleVideoInfoBean;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoID;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;
import com.microsys.videodemo.ui.contract.GetAllLocationContract;
import com.microsys.videodemo.utils.StringUtil;
import com.microsys.videodemo.utils.ThreadUtils;
import com.microsys.videodemo.utils.http.RubishHttpUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetAllLocationSimpleInfoPresenter extends RxBusinessPresenter<GetAllLocationContract.View>
        implements GetAllLocationContract.Presenter<GetAllLocationContract.View> {

    Api api;

    @Inject
    public GetAllLocationSimpleInfoPresenter(Api api) {
        this.api = api;
    }

    @Override
    public void getAllLocationSimpleInfo() {

        api.getAllVideo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SimpleVideoInfoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<SimpleVideoInfoBean> s) {
                        if (s!=null) {
                            view.getLocationInfoSucceed(s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.complete();
                    }
                });
    }

    @Override
    public void getVideoByIds(final ArrayList<VideoID> list) {

        ThreadUtils.doSingleThread(new Runnable() {
            @Override
            public void run() {
                String url = Api.BASE_URL + "GetVideoInfo";
                String result = "";
                try {
                    String json=new Gson().toJson(list);
                    result = new RubishHttpUtils().getPostData(url, "videoIDs",json);
                    if (StringUtil.isNotEmpty(result)) {
                        ArrayList<VideoInfoBean> list = new Gson().fromJson(result, new TypeToken<ArrayList<VideoInfoBean>>() {
                        }.getType());
                        if (null != list) {
                            view.getVideoByIdsSucceed(list);
                        }

                    } else {
                        view.complete();
                    }
                } catch (Exception e) {
                    view.complete();
                    view.showError(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

}
