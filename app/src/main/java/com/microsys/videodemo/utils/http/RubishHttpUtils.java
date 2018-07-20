package com.microsys.videodemo.utils.http;

import android.util.Log;

import com.microsys.videodemo.ui.bean.video_info_beans.VideoID;
import com.microsys.videodemo.utils.JniUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RubishHttpUtils {

    private static final String TAG = "RubishHttpUtils";

    private  Runnable timerOverRunnable = null;
    private  boolean isRecvDataFinished = false;


    class ParamsBean{
        private String key;
        private String value;

        public ParamsBean(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key == null ? "" : key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value == null ? "" : value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    /**
     * 获得rtsp数据
     */
    private static int connectTime = 0;

    public  String getPostData(String url,String... params){
        ParamsBean paramsBean;
        ArrayList<ParamsBean> paramsList=new ArrayList<>();
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("参数错误");
        }
        for (int i = 0; i < params.length; i += 2) {
            paramsBean=new ParamsBean(params[i],params[i+1]);
            paramsList.add(paramsBean);
        }

        timerOverRunnable = new Runnable() {

            @Override
            public void run() {

                isRecvDataFinished = true;
            }

        };

        HttpURLConnection httpConn = null;
        URL httpUrl = null;
        int res = 0;
        try {
            httpUrl = new URL(url);
            httpConn = (HttpURLConnection) httpUrl.openConnection();

            httpConn.setUseCaches(false);
            httpConn.setRequestProperty("pure-data", "yes");
            httpConn.setRequestProperty("Connection", "Keep-Alive");

            httpConn.setRequestProperty("Accept", "application/json");//接收json格式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");

            //设置请求体的类型是文本类型
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpConn.setRequestProperty("Accept-Encoding", "identity");
            for (ParamsBean param :
                    paramsList) {
                String key=param.getKey();
                String value=param.getValue();
                key= URLEncoder.encode(key,"utf-8");
                value=URLEncoder.encode(value,"utf-8");
                httpConn.setRequestProperty(key,value);
            }
//            httpConn.setRequestProperty("groupID", groupId);
//            httpConn.setRequestProperty("begin", begin);
//            httpConn.setRequestProperty("limit", limit);

            httpConn.setConnectTimeout(20*1000);
            httpConn.setReadTimeout(20*1000);
            //获得输出流，向服务器写入数据
//            OutputStream outputStream = httpConn.getOutputStream();
//            outputStream.write(data);
//            outputStream.flush();
//            outputStream.close();


            res = httpConn.getResponseCode();
            String result="";
            int total = httpConn.getContentLength();
            if (res == 200) {
                //定时器记时
                JniUtils.getInstance().timerOverTimeHandler.postDelayed(timerOverRunnable, 1 * 1000);

                InputStream in = httpConn.getInputStream();
                StringBuffer buffer = new StringBuffer();

                int c = 0;
                System.out.println("===========isRecvDataFinished==========="+isRecvDataFinished);
//                while(!isRecvDataFinished) {

                    byte[] buf = new byte[2048];
                    while ((c=in.read(buf)) > -1){
                        System.out.println("====c===="+c);

                        if(c < 2048) {//表示到最后了 水桶装不满了
                            byte[] buf_last = new byte[c];
                            System.arraycopy(buf, 0, buf_last, 0, c);

                            String temp = new String(buf_last,"gbk");
                            buffer.append(temp);
                        }else {
                            String temp = new String(buf,"GBK");
                            buffer.append(temp);
                        }

//                        //每次收到后，都把定时器清空
//                        JniUtils.getInstance().timerOverTimeHandler.removeCallbacks(timerOverRunnable);
//                        //开始重新计时，1秒
//                        JniUtils.getInstance().timerOverTimeHandler.postDelayed(timerOverRunnable, 1 * 1000);
                    }
//                }


               result = buffer.toString();

                Log.e(TAG, "getPostData: "+result );
                System.out.println("=====RtspData==result========="+result);

                //解析完成
//                GlobalJniManager.getInstance().getRtspDataManage().notifyGetRtspData(result);
                return result;

            }
            Log.e(TAG, "getPostData: "+result );
            return result;

        } catch (Exception e) {
            System.out.println("=======getrtspdata fail========="+e.getMessage());
            Log.e(TAG, "getPostData: "+e.getMessage() );
            e.printStackTrace();
            return "" ;
        }finally{

            if(null != httpConn) {
                httpConn.disconnect();
                httpConn = null;
            }

            connectTime = 0;
            isRecvDataFinished = false;
        }

    }

    public  String getPostData(String url,ArrayList<VideoID> params){
        ParamsBean paramsBean;
        ArrayList<ParamsBean> paramsList=new ArrayList<>();
        if (null==params||params.size()==0) {
            throw new IllegalArgumentException("参数错误");
        }


        timerOverRunnable = new Runnable() {

            @Override
            public void run() {

                isRecvDataFinished = true;
            }

        };

        HttpURLConnection httpConn = null;
        URL httpUrl = null;
        int res = 0;
        try {
            httpUrl = new URL(url);
            httpConn = (HttpURLConnection) httpUrl.openConnection();

            httpConn.setUseCaches(false);
            httpConn.setRequestProperty("pure-data", "yes");
            httpConn.setRequestProperty("Connection", "Keep-Alive");

            httpConn.setRequestProperty("Accept", "application/json");//接收json格式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");

            //设置请求体的类型是文本类型
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpConn.setRequestProperty("Accept-Encoding", "identity");
            for (int i = 0; i < params.size(); i++) {
                httpConn.setRequestProperty("videoIDs",params.get(i).getVideoId());
            }
//            httpConn.setRequestProperty("groupID", groupId);
//            httpConn.setRequestProperty("begin", begin);
//            httpConn.setRequestProperty("limit", limit);

            httpConn.setConnectTimeout(20*1000);
            httpConn.setReadTimeout(20*1000);
            //获得输出流，向服务器写入数据
//            OutputStream outputStream = httpConn.getOutputStream();
//            outputStream.write(data);
//            outputStream.flush();
//            outputStream.close();


            res = httpConn.getResponseCode();
            String result="";
            int total = httpConn.getContentLength();
            if (res == 200) {
                //定时器记时
                JniUtils.getInstance().timerOverTimeHandler.postDelayed(timerOverRunnable, 1 * 1000);

                InputStream in = httpConn.getInputStream();
                StringBuffer buffer = new StringBuffer();

                int c = 0;
                System.out.println("===========isRecvDataFinished==========="+isRecvDataFinished);
//                while(!isRecvDataFinished) {

                byte[] buf = new byte[2048];
                while ((c=in.read(buf)) > -1){
                    System.out.println("====c===="+c);

                    if(c < 2048) {//表示到最后了 水桶装不满了
                        byte[] buf_last = new byte[c];
                        System.arraycopy(buf, 0, buf_last, 0, c);

                        String temp = new String(buf_last,"gbk");
                        buffer.append(temp);
                    }else {
                        String temp = new String(buf,"gbk");
                        buffer.append(temp);
                    }

//                        //每次收到后，都把定时器清空
//                        JniUtils.getInstance().timerOverTimeHandler.removeCallbacks(timerOverRunnable);
//                        //开始重新计时，1秒
//                        JniUtils.getInstance().timerOverTimeHandler.postDelayed(timerOverRunnable, 1 * 1000);
                }
//                }


                result = buffer.toString();

                Log.e(TAG, "getPostData: "+result );
                System.out.println("=====RtspData==result========="+result);

                //解析完成
//                GlobalJniManager.getInstance().getRtspDataManage().notifyGetRtspData(result);
                return result;

            }
            Log.e(TAG, "getPostData: "+result );
            return result;

        } catch (Exception e) {
            System.out.println("=======getrtspdata fail========="+e.getMessage());
            Log.e(TAG, "getPostData: "+e.getMessage() );
            e.printStackTrace();
            return "" ;
        }finally{

            if(null != httpConn) {
                httpConn.disconnect();
                httpConn = null;
            }

            connectTime = 0;
            isRecvDataFinished = false;
        }

    }

}
