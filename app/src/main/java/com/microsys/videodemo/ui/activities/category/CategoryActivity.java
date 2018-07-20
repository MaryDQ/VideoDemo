package com.microsys.videodemo.ui.activities.category;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.microsys.videodemo.HomeActivity;
import com.microsys.videodemo.R;
import com.microsys.videodemo.base.BaseActivity;
import com.microsys.videodemo.common.Constants;
import com.microsys.videodemo.di.component.AppComponent;
import com.microsys.videodemo.di.component.DaggerCommonActivityComponent;
import com.microsys.videodemo.eventbus_events.DismissDialogEvent;
import com.microsys.videodemo.ui.activities.location.LocationActivity;
import com.microsys.videodemo.ui.activities.media.video.PlayVideoActivity;
import com.microsys.videodemo.ui.bean.TestBean;
import com.microsys.videodemo.ui.bean.video_info_beans.GroupOrVideoBean;
import com.microsys.videodemo.ui.contract.NewCategoryListContract;
import com.microsys.videodemo.ui.presenter.NewCategoryListPresenter;
import com.microsys.videodemo.utils.IntentUtils;
import com.microsys.videodemo.utils.StringUtil;
import com.microsys.videodemo.utils.ToastUtils;
import com.microsys.videodemo.widget.MyRecyclerViews.SimpleAdapter.AbstractSimpleAdapter;
import com.microsys.videodemo.widget.MyRecyclerViews.SimpleAdapter.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CategoryActivity extends BaseActivity<NewCategoryListPresenter> implements NewCategoryListContract.View {

    @BindView(R.id.tv_jump_to_map)
    TextView mTvJumpToMap;
    @BindView(R.id.tvRight)
    TextView mTvRight;
    @BindView(R.id.rcv_category)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_category)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<TestBean> testList = new ArrayList<>();//RecycleView的数据

    private AbstractSimpleAdapter<String> mRecyclerAdapter;//RecycleAdapter

    private int lastVisibleItem;//RecyclerView最后一个可见的Item

    private String firstGroupId;//传过来展示的groupID

    private String firstGroupName;//传过来展示的groupName

    private String fromWhich = "";//从哪个Activity跳转过来的

    private String mCurGroupId;//当前的groupID

    private int selectedItem = 0;

    private boolean isFromRefreshing = false;

    private List<List<GroupOrVideoBean.GroupsBean>> mTreeGroupList = new ArrayList<>();//group树

    private List<GroupOrVideoBean.VideosBean> mVideoInfoBeanList = new ArrayList<>();//需要展示的Video

    private ArrayList<String> mList = new ArrayList<>();

    private String mCurGroupName = "";

    private int beginItem = 0;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public int getRootViewId() {
        return R.layout.activity_category;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommonActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initViewAndData() {
        initIntent();
        initViews();//findViewById和setText()
        initAdapter();
        if (StringUtil.isNotEmpty(firstGroupId)) {
            //假装请求了网络
            showLoading(getString(R.string.loading));
            presenter.getNewCategoryList(firstGroupId, firstGroupName, beginItem, 20);
        }

    }

    private void initIntent() {
        Intent intent = getIntent();
        fromWhich = intent.getStringExtra("fromWhich");
        firstGroupId = intent.getStringExtra("groupID");
        firstGroupName = intent.getStringExtra("groupName");
        selectedItem = intent.getIntExtra("selectedItem", 0);
        mCurGroupId = firstGroupId;
        mCurGroupName = firstGroupName;
    }

    private void initViews() {
        setCustomTitle("胜利油田云视频");
        mTvRight.setText("返回首页");
        mTvRight.setVisibility(View.VISIBLE);
    }

    private void initAdapter() {
        mRecyclerAdapter = new AbstractSimpleAdapter<String>(mContext, mList, R.layout.item_simple_tv) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, String data) {
                if (StringUtil.isNotEmpty(data)) {
                    try {
                        data = URLDecoder.decode(data, "UTF-8");
                        if (StringUtil.isEmpty(data)) {
                            holder.<TextView>getView(R.id.tv_item_simple).setText("未知");
                        } else {
                            holder.<TextView>getView(R.id.tv_item_simple).setText(data);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerAdapter.setOnItemClickListener(new AbstractSimpleAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(Object o, int position) {
                try {
                    if (null != mVideoInfoBeanList && mVideoInfoBeanList.size() > 0) {
                        GroupOrVideoBean.VideosBean bean = mVideoInfoBeanList.get(position);
                        if (null != bean) {
                            Intent intent = new Intent(CategoryActivity.this, PlayVideoActivity.class);
                            intent.putExtra("tempItemNewsByGroupOrVideo", bean);
                            startActivity(intent);
                        }

                    } else {
                        GroupOrVideoBean.GroupsBean bean = mTreeGroupList.get(mTreeGroupList.size() - 1).get(position);
                        if (null != bean) {
                            showLoading();
                            beginItem = 0;
                            presenter.getNewCategoryList(bean.getGroupID(), bean.getGroupName(), beginItem, 20);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mSwipeRefreshLayout.setEnabled(false);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_SETTLING && lastVisibleItem + 1 == mRecyclerAdapter.getItemCount()) {
                    showLoading();
                    beginItem += 20;
                    isFromRefreshing = true;
                    presenter.getNewCategoryList(mCurGroupId, mCurGroupName, beginItem, 20);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        mRecyclerAdapter.notifyDataSetChanged();


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

    @OnClick({R.id.tv_jump_to_map, R.id.tvLeft, R.id.tvRight})
    public void viewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_jump_to_map:
                IntentUtils.jumpToTargetClass(mContext, LocationActivity.class);
                break;
            case R.id.tvLeft:
                backEvents();
                break;
            case R.id.tvRight:
                Intent intent = new Intent(CategoryActivity.this, HomeActivity.class);
                intent.putExtra("groupID", mCurGroupId);
                intent.putExtra("selectedItem", selectedItem);
                intent.putExtra("groupName", mCurGroupName);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void backEvents() {

        if (null == mTreeGroupList && null == mVideoInfoBeanList) {
            return;
        }
        if ("推荐".equals(mCurGroupName)) {
            finish();
            return;
        }
        if (null != mTreeGroupList && mTreeGroupList.size() > 0 && null != mVideoInfoBeanList && 0 == mVideoInfoBeanList.size()) {
            int size = mTreeGroupList.size();
            //如果是最上面的树,那就直接返回
            if (size == 1) {
                finish();
                return;
            }

            mTreeGroupList.remove(size - 1);
            mList = new ArrayList<>();
            List<GroupOrVideoBean.GroupsBean> beanList = mTreeGroupList.get(size - 2);
            mList = new ArrayList<>();
            for (int i = 0; i < beanList.size(); i++) {
                mList.add(beanList.get(i).getGroupName());
            }
            initAdapter();
        } else if (null != mTreeGroupList && null != mVideoInfoBeanList && mVideoInfoBeanList.size() > 0) {
            mVideoInfoBeanList = new ArrayList<>();
            int size = mTreeGroupList.size();
            if (size > 0) {
                List<GroupOrVideoBean.GroupsBean> beanList = mTreeGroupList.get(size - 1);
                mList = new ArrayList<>();
                for (int i = 0; i < beanList.size(); i++) {
                    mList.add(beanList.get(i).getGroupName());
                }
                initAdapter();
            }else {
                finish();
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (isDialogShowing()) {
            Constants.isUserCancelDialog = true;
            dismissLoading();
        }
        backEvents();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DismissDialogEvent event) {
        if (event.isRefresh()) {
            dismissLoading();
        }
    }

    @Override
    public void newCategoryListSucceed(List<GroupOrVideoBean.GroupsBean> groupBeanList, List<GroupOrVideoBean.VideosBean> infoBeanList, String beforeGroupId, String beforeGroupName) {
        if (Constants.isUserCancelDialog) {
            return;
        }

        boolean ifHavaCameraInfo = null == groupBeanList && null == infoBeanList || null == mList;
        if (ifHavaCameraInfo) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dismissLoading();
                    ToastUtils.showToast("无摄像头信息");
                }
            });
            return;
        }

        mCurGroupId = beforeGroupId;
        mCurGroupName = beforeGroupName;

        //如果当前组名是推荐
        boolean isRecommend = doWhileGroupIsRecommend(infoBeanList);
        if (isRecommend) {
            return;
        }

        if (null != groupBeanList) {
            doWhileRcvIsGroup(groupBeanList);
            return;
        }
        if (null != infoBeanList) {
            doWhileRcvIsVideo(infoBeanList);
        }
    }

    private boolean doWhileGroupIsRecommend(List<GroupOrVideoBean.VideosBean> infoBeanList) {

        if ("推荐".equals(mCurGroupName)) {
            if (infoBeanList.size() > 0) {
                mVideoInfoBeanList.addAll(infoBeanList);
                if (isFromRefreshing) {

                    isFromRefreshing = false;
                    for (GroupOrVideoBean.VideosBean bean :
                            infoBeanList) {
                        mList.add(bean.getVideoName());
                    }
                } else {
                    mList.clear();
                    for (GroupOrVideoBean.VideosBean bean :
                            infoBeanList) {
                        mList.add(bean.getVideoName());
                    }
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        initAdapter();
                        int size = mRecyclerAdapter.getItemCount();
                        if (null == mRecyclerAdapter || 0 == size) {
                            initAdapter();
                        }
                        mRecyclerAdapter.notifyDataSetChanged();
                        dismissLoading();
                    }
                });
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "newCategoryListSucceed: 获取videoInfoList的size为0");
                        dismissLoading();
                    }
                });
            }
            return true;
        }
        return false;
    }

    private void doWhileRcvIsGroup(List<GroupOrVideoBean.GroupsBean> groupBeanList) {
        if (groupBeanList.size() > 0) {

            if (isFromRefreshing) {
                mTreeGroupList.get(mTreeGroupList.size() - 1).addAll(groupBeanList);
                isFromRefreshing = false;
                for (int i = 0; i < groupBeanList.size(); i++) {
                    String name = groupBeanList.get(i).getGroupName();
                    try {
                        name = URLDecoder.decode(name, "UTF-8");
                        mList.add(name);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                mTreeGroupList.add(groupBeanList);
                mList.clear();
                for (int i = 0; i < groupBeanList.size(); i++) {
                    String name = groupBeanList.get(i).getGroupName();
                    try {
                        name = URLDecoder.decode(name, "UTF-8");
                        mList.add(name);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    int size = mRecyclerAdapter.getItemCount();
                    if (null == mRecyclerAdapter || 0 == size) {
                        initAdapter();
                    }
                    mRecyclerAdapter.notifyDataSetChanged();
                    dismissLoading();
                }
            });
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "newCategoryListSucceed: 获取groupList的size为0");
                    dismissLoading();
                }
            });
        }
    }

    private void doWhileRcvIsVideo(List<GroupOrVideoBean.VideosBean> infoBeanList) {
        if (infoBeanList.size() > 0) {
            mVideoInfoBeanList.addAll(infoBeanList);
            if (isFromRefreshing) {

                isFromRefreshing = false;
                for (GroupOrVideoBean.VideosBean bean :
                        infoBeanList) {
                    mList.add(bean.getVideoName());
                }
            } else {
                mList.clear();
                for (GroupOrVideoBean.VideosBean bean :
                        infoBeanList) {
                    mList.add(bean.getVideoName());
                }
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
//                        initAdapter();
                    int size = mRecyclerAdapter.getItemCount();
                    if (null == mRecyclerAdapter || 0 == size) {
                        initAdapter();
                    }
                    mRecyclerAdapter.notifyDataSetChanged();
                    dismissLoading();
                }
            });
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "newCategoryListSucceed: 获取videoInfoList的size为0");
                    dismissLoading();
                }
            });
        }
    }

    @Override
    public void showError(String msg) {
        final String error = msg;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "showError:" + error);
                dismissLoading();
            }
        });
    }

    @Override
    public void complete() {

    }
}
