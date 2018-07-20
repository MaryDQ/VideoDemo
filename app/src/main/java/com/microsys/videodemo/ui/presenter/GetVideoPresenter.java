package com.microsys.videodemo.ui.presenter;

import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsys.videodemo.api.Api;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;
import com.microsys.videodemo.ui.contract.GetVideoContract;
import com.microsys.videodemo.utils.StringUtil;
import com.microsys.videodemo.utils.ThreadUtils;
import com.microsys.videodemo.utils.http.RubishHttpUtils;

import java.util.List;

import javax.inject.Inject;

public class GetVideoPresenter extends RxBusinessPresenter<GetVideoContract.View>
        implements GetVideoContract.Presenter<GetVideoContract.View> {

    private static final String TAG = "GetVideoPresenter";

    Api api;

    @Inject
    public GetVideoPresenter(Api api) {
        this.api = api;
    }

    @Override
    public void getVideos(final String groupId, final int begin, final int limit) {
        // TODO: 2018/7/6 通过groupId请求组下面的信息
        final String url = Api.BASE_URL + "GetVideo";
        try {
            ThreadUtils.doSingleThread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        Log.e(TAG, "videoFragment数据:"+groupId);
                        String result = new RubishHttpUtils().getPostData(url, "groupID", groupId, "begin", begin + "", "limit", limit + "");
                        Log.e(TAG, "videoFragment数据:"+result );
                        if (StringUtil.isNotEmpty(result)) {
                            List<VideoInfoBean> list = new Gson().fromJson(result, new TypeToken<List<VideoInfoBean>>() {
                            }.getType());
                            if (null != list) {
                                view.getVideosSucceed(list);
                            }
                        } else {
                            view.complete();
                        }
                    } catch (Exception e) {
                        view.complete();
                        view.showError(e.getMessage());
                        e.printStackTrace();
                    }
                    Looper.loop();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getVideos: " + e.toString());
        }
    }

}
