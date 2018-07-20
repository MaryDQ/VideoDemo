package com.microsys.videodemo.ui.activities.media.video;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.microsys.videodemo.R;
import com.microsys.videodemo.base.BaseActivity;
import com.microsys.videodemo.common.Constants;
import com.microsys.videodemo.di.component.AppComponent;
import com.microsys.videodemo.eventbus_events.DismissDialogEvent;
import com.microsys.videodemo.ui.adapter.RecycleAdapter;
import com.microsys.videodemo.ui.bean.video_info_beans.GroupOrVideoBean;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;
import com.microsys.videodemo.utils.ToastUtils;
import com.microsys.videodemo.utils.VideoCommonUtils;
import com.microsys.videodemo.widget.LandLayoutVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.GSYVideoShotListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.model.VideoOptionModel;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * @author mlx
 */
public class PlayVideoActivity extends BaseActivity {
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.rcv_play_video)
    RecyclerView mRecyclerView;

    @BindView(R.id.rtsp_video)
    LandLayoutVideo mPlayer;

    private boolean isPlay;//播放中
    private boolean isPause;//暂停中
    private boolean fromLocationAct = false;

    private OrientationUtils orientationUtils;

    //rtsp播放链接
    private String mPlayUrl = "";
    //rtsp摄像头名称
    private String mVideoName = "";

    private RecycleAdapter mRecycleAdapter;
    private ArrayList<VideoInfoBean> testList = new ArrayList<>();
    private VideoInfoBean receivedTempItemNews;
    private GroupOrVideoBean.VideosBean receivedVideoBean;
    private GSYVideoOptionBuilder gsyVideoOption;
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    public void onBackPressed() {

        if (isDialogShowing()) {
            Constants.isUserCancelDialog = true;
            dismissLoading();
        }

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        finish();
    }

    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    private GSYVideoPlayer getCurPlay() {
        if (null != mPlayer && mPlayer.getFullWindowPlayer() != null) {
            return mPlayer.getFullWindowPlayer();
        }
        return mPlayer;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DismissDialogEvent event) {
        if (event.isRefresh()) {
            dismissLoading();
        }
    }

    @Override
    protected void onDestroy() {

        if (isPlay) {
            getCurPlay().release();
        }
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }

        mRecyclerView = null;
        super.onDestroy();
    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_play_video;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initViewAndData() {

        //rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov
        // rtsp://112.17.124.173:554/tel:223344
        // rtsp://admin:12345@112.17.124.183:555
        initIntent();
        initAdapter();
        initVideoPlayer();
    }

    private void initIntent() {
        try {
            Intent intent = getIntent();
            fromLocationAct = intent.getBooleanExtra("fromLocationAct", false);
            receivedTempItemNews = (VideoInfoBean) intent.getSerializableExtra("tempItemNews");
            receivedVideoBean = (GroupOrVideoBean.VideosBean) intent.getSerializableExtra("tempItemNewsByGroupOrVideo");
            //从地图跳转过来的数据
            if (fromLocationAct) {
                VideoInfoBean videoInfoBean = (VideoInfoBean) getIntent().getSerializableExtra("videoInfoBean");
                mPlayUrl = getRealPlayUrl(videoInfoBean);
            } else {
                //分组界面跳转进入
                if (null != receivedTempItemNews) {
                    mPlayUrl = getRealPlayUrl(receivedTempItemNews);
                }//搜索界面或者首页点击跳转进入
                else if (null != receivedVideoBean) {
                    mPlayUrl = getRealPlayUrl(receivedVideoBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        mRecycleAdapter = new RecycleAdapter(testList, mContext);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2, RecyclerView.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecycleAdapter);
        mRecycleAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPlayer.onVideoPause();
                isPause = true;
                changePlayUrl(gsyVideoOption, mPlayUrl, mVideoName);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLoading("切换中");
                    }
                });
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoading();
                        mPlayer.startPlayLogic();
                        isPause = false;
                    }
                }, 1000);

            }
        });
    }

    private void initVideoPlayer() {
        VideoOptionModel videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 25);
        VideoOptionModel videoOptionModel01 = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
        //如果视频seek之后从头播放
//        VideoOptionModel videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);
        List<VideoOptionModel> list = new ArrayList<>();
        list.add(videoOptionModel);
        list.add(videoOptionModel01);
        GSYVideoManager.instance().setOptionModelList(list);
        GSYVideoManager.instance().setTimeOut(4000, true);

        resolveNormalVideoUI();
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, mPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        gsyVideoOption = new GSYVideoOptionBuilder();
//        mPlayUrl = "rtsp://admin:12345@112.17.124.183:555";
        changePlayUrl(gsyVideoOption, mPlayUrl, mVideoName);
        mPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                mPlayer.startWindowFullscreen(PlayVideoActivity.this, true, true);
            }
        });

        mPlayer.getShotImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shotImage(view);
            }
        });

        mPlayer.startPlayLogic();
        isPause = false;


    }

    private String getRealPlayUrl(Object object) {
        String realPlayUrl = "";
        try {
            if (object instanceof VideoInfoBean) {
                VideoInfoBean videoInfoBean = (VideoInfoBean) object;
                if (!"".equals(videoInfoBean.getUserName()) && !"".equals(videoInfoBean.getUserPwd())) {
                    realPlayUrl = "rtsp://" + videoInfoBean.getUserName() + ":" + videoInfoBean.getUserPwd() + "@" + videoInfoBean.getiPAddr() + ":" + videoInfoBean.getiPPort() + "/" + videoInfoBean.getPuid();
                } else {
                    realPlayUrl = "rtsp://" + videoInfoBean.getiPAddr() + ":" + videoInfoBean.getiPPort() + "/" + videoInfoBean.getPuid();
                }
                mVideoName = videoInfoBean.getVideoName();
            } else if (object instanceof GroupOrVideoBean.VideosBean) {
                GroupOrVideoBean.VideosBean videosBean = (GroupOrVideoBean.VideosBean) object;
                if (!"".equals(videosBean.getUserName()) && !"".equals(videosBean.getUserPwd())) {
                    realPlayUrl = "rtsp://" + videosBean.getUserName() + ":" + videosBean.getUserPwd() + "@" + videosBean.getiPAddr() + ":" + videosBean.getiPPort() + "/" + videosBean.getPuid();
                } else {
                    realPlayUrl = "rtsp://" + videosBean.getiPAddr() + ":" + videosBean.getiPPort() + "/" + videosBean.getPuid();
                }
                mVideoName = videosBean.getVideoName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return realPlayUrl;
    }

    private void changePlayUrl(GSYVideoOptionBuilder tempGsyVideoOption, String url, String videoName) {
        tempGsyVideoOption
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(url)
                .setCacheWithPlay(false)
                .setVideoTitle(videoName)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(final String url, Object... objects) {
                        Debuger.printfError("***** onPrepared **** " + objects[0]);
                        Debuger.printfError("***** onPrepared **** " + objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }

                    @Override
                    public void onPlayError(String url, Object... objects) {
                        super.onPlayError(url, objects);
                        if (null != getCurPlay()) {
                            getCurPlay().startPlayLogic();
                        } else {
                            Log.e(TAG, "当前player对象为空!");
                        }

                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                })
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                        Debuger.printfLog(" progress " + progress + " secProgress " + secProgress + " currentPosition " + currentPosition + " duration " + duration);
                    }
                })
                .build(mPlayer);

        mPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                mPlayer.startWindowFullscreen(PlayVideoActivity.this, true, true);
            }
        });
    }

    private void resolveNormalVideoUI() {
        //增加title
        mPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        mPlayer.getBackButton().setVisibility(View.VISIBLE);
        mPlayer.getShotImageView().setVisibility(View.VISIBLE);
    }

    private void shotImage(final View v) {
        mPlayer.taskShotPic(new GSYVideoShotListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                if (bitmap != null) {
                    try {
                        VideoCommonUtils.saveBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        ToastUtils.showToast("save fail");
                        e.printStackTrace();
                        return;
                    }
                    ToastUtils.showToast("save success");
                } else {
                    ToastUtils.showToast("get bitmap fail");
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            mPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tvLeft})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvLeft:
                if (isDialogShowing()) {
                    Constants.isUserCancelDialog = true;
                    dismissLoading();
                }

                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }

                if (GSYVideoManager.backFromWindowFull(this)) {
                    return;
                }
                finish();
                break;
            default:
                break;
        }

    }


}
