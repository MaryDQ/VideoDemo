package com.microsys.videodemo.api;

import com.google.gson.Gson;
import com.microsys.videodemo.http.BaseResponse;
import com.microsys.videodemo.ui.bean.video_info_beans.GroupOrVideoBean;
import com.microsys.videodemo.ui.bean.video_info_beans.SimpleVideoInfoBean;
import com.microsys.videodemo.utils.StringUtil;
import com.microsys.videodemo.utils.http.EncryptionUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author mlx
 */
public class Api {
    /**
     * baseUrl 10.66.6.183
     */
    public static final String BASE_URL_WITHOUT_PORT = "http://10.60.6.183:";
    public static final String BASE_URL=BASE_URL_WITHOUT_PORT+"8081/";

    private ApiService apiService;
    private Gson gson;
    private OkHttpClient okHttpClient;

    public Api(Retrofit retrofit, Gson gson, OkHttpClient okHttpClient) {
        this.gson = gson;
        this.okHttpClient = okHttpClient;
        apiService = retrofit.create(ApiService.class);
    }


    public Gson getGson() {
        return gson;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public Observable<BaseResponse> doBusiness(Map map) {
        return apiService.doBusiness(composeJson(map));
    }

    private String composeJson(Map map) {
        return gson.toJson(map);
    }

    public Observable<BaseResponse> getVideoRecommend() {
        return apiService.getVideoRecommend();
    }

    public Observable<GroupOrVideoBean> getGroupOrVideo() {
        return apiService.getGroupOrVideo();
    }

    public Observable<List<SimpleVideoInfoBean>> getAllVideo() {
        return apiService.getAllVideo();
    }


    public String signature(String httpType, String json, String url, String date, String encryptKey) throws Exception {

        String stringToSign;
        if (StringUtil.isEmpty(json)) {
            stringToSign =
                    //HTTP 协议 Header
                    httpType + "\n" + date + "\n" + "\n" + "\n" + url;
        } else {
            String content = EncryptionUtil.md5(json);
            stringToSign =
                    //HTTP 协议 Header
                    httpType + "\n" + date + "\n" + content + "\n" + url;
        }
//        String uftStr = toUtf8(StringToSign);
        System.out.println("====StringToSign====StringToSign====" + stringToSign);
        byte[] hmac = EncryptionUtil.HmacSHA1Encrypt(stringToSign, encryptKey);
        StringBuilder builder = new StringBuilder(hmac.length);
        for (byte b : hmac) {
            int i = b & 0xff;
            if (i <= 0xf) {
                builder.append("0");
            }
            builder.append(Integer.toHexString(i));
        }
        String macStr = builder.toString();
        System.out.println("====macStr====macStr====" + macStr);
        String signature = EncryptionUtil.stringToBase64(macStr);
        System.out.println("====Signature====Signature====" + signature);
        return signature;
    }
}
