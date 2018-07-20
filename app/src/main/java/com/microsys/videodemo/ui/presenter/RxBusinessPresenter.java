package com.microsys.videodemo.ui.presenter;


import com.microsys.videodemo.api.Api;
import com.microsys.videodemo.base.mvp.IView;
import com.microsys.videodemo.base.mvp.RxPresenter;
import com.microsys.videodemo.http.BaseResponse;
import com.microsys.videodemo.utils.L;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ============================
 * 作    者：mlx
 * 创建日期：2018/1/22.
 * 描    述：BusinessRx接口基类
 * 修改历史：
 * ===========================
 */

public abstract class RxBusinessPresenter<T extends IView> extends RxPresenter<T> {

    protected Api api;

    protected RxBusinessPresenter setApi(Api api) {
        this.api = api;
        return this;
    }

    public void requestData(Map map, final ResponseResult responseResult) {
        api.doBusiness(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubcribe(d);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        //网络请求结果
                        if (baseResponse.getCode() == 0) {
                            responseResult.success(api.getGson().toJson(baseResponse.getBody()));
                        } else if (baseResponse.getCode() == 4) {
                            responseResult.failed(baseResponse.getMsg());
                        } else {
                            view.showError(baseResponse.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.toString());
                        L.e(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.complete();
                    }
                });
    }

    interface ResponseResult {
        void success(String body);
        void failed(String body);
    }
}
