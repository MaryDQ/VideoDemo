package com.microsys.videodemo.ui.presenter;

import com.microsys.videodemo.api.Api;
import com.microsys.videodemo.ui.bean.video_info_beans.GroupOrVideoBean;
import com.microsys.videodemo.ui.contract.GetRecommendListContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetRecommendListPresenter extends RxBusinessPresenter<GetRecommendListContract.View>
        implements GetRecommendListContract.Presenter<GetRecommendListContract.View> {

    private static final String TAG = "NewCategoryListPresente";

    Api api;

    @Inject
    public GetRecommendListPresenter(Api api) {
        this.api = api;
    }


    @Override
    public void getRecommendList() {
        api.getGroupOrVideo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GroupOrVideoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GroupOrVideoBean s) {
                        if (null!=s) {

                            view.getRecommendListSucceed(s.getGroups());
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

}