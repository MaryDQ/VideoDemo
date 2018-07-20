package com.microsys.videodemo.ui.presenter;

import com.google.gson.Gson;
import com.microsys.videodemo.api.Api;
import com.microsys.videodemo.ui.bean.video_info_beans.GroupOrVideoBean;
import com.microsys.videodemo.ui.contract.NewCategoryListContract;
import com.microsys.videodemo.utils.ThreadUtils;
import com.microsys.videodemo.utils.http.RubishHttpUtils;

import javax.inject.Inject;

public class NewCategoryListPresenter extends RxBusinessPresenter<NewCategoryListContract.View>
        implements NewCategoryListContract.Presenter<NewCategoryListContract.View> {

    private static final String TAG = "NewCategoryListPresente";

    Api api;

    @Inject
    public NewCategoryListPresenter(Api api) {
        this.api = api;
    }

    @Override
    public void getNewCategoryList(String ids, String name, final int begin, final int limit) {

        final String groupId = ids;
        final String groupName = name;
        final int beginItem = begin;
        final int limitNums = limit;
        ThreadUtils.doSingleThread(new Runnable() {
            @Override
            public void run() {
                String url = Api.BASE_URL + "GetGroupOrVideo";
                String result = "";
                try {
                    result = new RubishHttpUtils().getPostData(url, "groupID", groupId, "begin", begin + "", "limit", limit + "");
                    if (null != result) {
                        GroupOrVideoBean bean = new Gson().fromJson(result, GroupOrVideoBean.class);
                        if (result.contains("videos\":[]") && result.contains("groups\":[]")) {
                            view.newCategoryListSucceed(null, null, groupId, groupName);
                        } else if (result.contains("videos\":[]")) {
                            view.newCategoryListSucceed(bean.getGroups(), null, groupId, groupName);
                        } else {
                            view.newCategoryListSucceed(null, bean.getVideos(), groupId, groupName);
                        }
                    } else {
                        view.newCategoryListSucceed(null, null, groupId, groupName);
                    }
                } catch (Exception e) {
                    view.showError(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
