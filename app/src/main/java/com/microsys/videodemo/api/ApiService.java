package com.microsys.videodemo.api;

import com.microsys.videodemo.http.BaseResponse;
import com.microsys.videodemo.ui.bean.video_info_beans.GroupOrVideoBean;
import com.microsys.videodemo.ui.bean.video_info_beans.SimpleVideoInfoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST()
    Observable<BaseResponse> doBusiness(@Field("ids") String ids);

    @GET("Recommend")
    Observable<BaseResponse> getVideoRecommend();

    @GET("GetGroupOrVideo")
    Observable<GroupOrVideoBean> getGroupOrVideo();

    @GET("GetAllVideo")
    Observable<List<SimpleVideoInfoBean>> getAllVideo();

}
