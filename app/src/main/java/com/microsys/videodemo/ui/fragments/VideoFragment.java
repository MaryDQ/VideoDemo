package com.microsys.videodemo.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.microsys.videodemo.R;
import com.microsys.videodemo.base.BaseFragment;
import com.microsys.videodemo.common.Constants;
import com.microsys.videodemo.di.component.AppComponent;
import com.microsys.videodemo.di.component.DaggerCommonFragmentComponent;
import com.microsys.videodemo.eventbus_events.DismissDialogEvent;
import com.microsys.videodemo.eventbus_events.HomeActivityRefreshEvent;
import com.microsys.videodemo.eventbus_events.HomeActivitySwitchFragEvent;
import com.microsys.videodemo.ui.activities.media.video.PlayVideoActivity;
import com.microsys.videodemo.ui.adapter.RecycleAdapter;
import com.microsys.videodemo.ui.bean.video_info_beans.GroupOrVideoBean;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;
import com.microsys.videodemo.ui.contract.GetVideoContract;
import com.microsys.videodemo.ui.presenter.GetVideoPresenter;
import com.microsys.videodemo.utils.StringUtil;
import com.microsys.videodemo.utils.banner_image_loader.BannerImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author mlx
 */
public class VideoFragment extends BaseFragment<GetVideoPresenter> implements GetVideoContract.View {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rcv_fragment)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_video_fragment)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.nsv_video_fragment)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.tv_videofrag_cur_showing)
    TextView mTvCurShowing;

    private GroupOrVideoBean.GroupsBean mGroupsBean;

    private ArrayList<VideoInfoBean> testList = new ArrayList<>();

    private RecycleAdapter mRecyclerAdapter;


    private List<Object> images = new ArrayList<>();

    private int lastVisibleItem;

    private boolean canRefresh = false;

    private String curGroupID;

    private int beginItem = 0;

    private List<VideoInfoBean> receivedVideoInfoBsList;

    private boolean isRequestFromLocal = false;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int id = msg.what;
            switch (id) {
                case 1000:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public VideoFragment() {
    }

    @SuppressLint("ValidFragment")
    public VideoFragment(GroupOrVideoBean.GroupsBean groupsBean, boolean isRequestFromLocal) {
        mGroupsBean = groupsBean;
        curGroupID = mGroupsBean.getGroupID();
        this.isRequestFromLocal = isRequestFromLocal;
    }

    @SuppressLint("ValidFragment")
    public VideoFragment(List<VideoInfoBean> list, boolean isRequestFromLocal) {
        this.receivedVideoInfoBsList = list;
        this.isRequestFromLocal = isRequestFromLocal;
    }


    public void setGroupsBean(GroupOrVideoBean.GroupsBean groupsBean) {
        mGroupsBean = groupsBean;
    }


    @Override
    public int getRootViewId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommonFragmentComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initViewAndData() {
        if (!isRequestFromLocal) {
            if (null != mGroupsBean) {
                presenter.getVideos(mGroupsBean.getGroupID(), beginItem, 20);
            }
        }


        initBanner();
        initAdapter();
        if (isRequestFromLocal) {
            setList(receivedVideoInfoBsList);
        }
    }

    private void initBanner() {
        images.add(R.mipmap.banner_01);
        images.add(R.mipmap.banner_02);
        images.add(R.mipmap.banner_03);
        images.add(R.mipmap.banner_04);
        images.add(R.mipmap.banner_05);
        images.add(R.mipmap.banner_06);
        images.add(R.mipmap.banner_07);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new BannerImageLoader());
        banner.update(images);
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position != Constants.CUR_FRAGMENT_ITEM) {
                    EventBus.getDefault().post(new HomeActivitySwitchFragEvent(true, position));
                }
            }
        });
    }

    private void initAdapter() {
        if (null == testList) {
            return;
        }
        mRecyclerAdapter = new RecycleAdapter(testList, mContext);
        final GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2, RecyclerView.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, PlayVideoActivity.class);
                intent.putExtra("tempItemNews", testList.get(position));
                mContext.startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mSwipeRefreshLayout.setEnabled(false);

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d("scrollChange:", "oldScrollY=" + oldScrollY + ",ScrollY=" + scrollY);
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    showLoading(getString(R.string.loading));
                    beginItem += 20;
                    presenter.getVideos(curGroupID, beginItem, 20);
                }
            }


        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_SETTLING && lastVisibleItem + 1 == mRecyclerAdapter.getItemCount()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }, 3000);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

    }

    /**
     * 加载数据刷新列表
     *
     * @param list 返回来的视频列表
     */
    private void setList(List<VideoInfoBean> list) {
        if (null == list || 0 == list.size()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dismissLoading();
                }
            });
            return;
        }
        Log.e("VideoFragment", "setList: " + list.size());
        if (null == mRecyclerAdapter) {
            initAdapter();
        }

        testList.addAll(list);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerAdapter.notifyDataSetChanged();
                dismissLoading();
            }
        });

        EventBus.getDefault().post(new HomeActivityRefreshEvent(true, "取消加载框"));
    }

    public void refreshByGroupId(String groupID, String groupName) {
        if (StringUtil.isEmpty(groupID) || StringUtil.isEmpty(groupName)) {
            return;
        }
        beginItem = 0;
        curGroupID = groupID;
        mTvCurShowing.setText(groupName);
        mTvCurShowing.setVisibility(View.VISIBLE);
        showLoading(getString(R.string.loading));

        presenter.getVideos(groupID, beginItem, 20);
    }

    @Override
    public void onStart() {
        banner.startAutoPlay();
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onPause() {
        banner.stopAutoPlay();
        EventBus.getDefault().unregister(this);
        super.onPause();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DismissDialogEvent event) {
        if (event.isRefresh()) {
            Constants.isUserCancelDialog = true;
            dismissLoading();
        }
    }

    @Override
    public void getVideosSucceed(List<VideoInfoBean> list) {
        if (Constants.isUserCancelDialog) {

            return;
        }
        //刷新列表
        setList(list);
    }

    @Override
    public void showError(String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                dismissLoading();

            }
        });
    }

    @Override
    public void complete() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
            }
        });
    }


}
