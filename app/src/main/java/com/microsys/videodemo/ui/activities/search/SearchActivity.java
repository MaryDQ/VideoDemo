package com.microsys.videodemo.ui.activities.search;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.microsys.videodemo.R;
import com.microsys.videodemo.base.BaseNoTitleActivity;
import com.microsys.videodemo.common.Constants;
import com.microsys.videodemo.di.component.AppComponent;
import com.microsys.videodemo.di.component.DaggerCommonActivityComponent;
import com.microsys.videodemo.eventbus_events.DismissDialogEvent;
import com.microsys.videodemo.ui.activities.media.video.PlayVideoActivity;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;
import com.microsys.videodemo.ui.contract.SearchVideoContract;
import com.microsys.videodemo.ui.presenter.SearchVideoPresenter;
import com.microsys.videodemo.utils.InputMethodUtils;
import com.microsys.videodemo.utils.ToastUtils;
import com.microsys.videodemo.widget.MyRecyclerViews.SimpleAdapter.AbstractSimpleAdapter;
import com.microsys.videodemo.widget.MyRecyclerViews.SimpleAdapter.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author mlx
 */
public class SearchActivity extends BaseNoTitleActivity<SearchVideoPresenter> implements SearchVideoContract.View {
    @BindView(R.id.editSearch)
    EditText mEditSearch;
    @BindView(R.id.tvCancel)
    TextView mTvCancel;
    @BindView(R.id.rcv_search)
    RecyclerView mRcvSearch;

    private AbstractSimpleAdapter<VideoInfoBean> mAdapter;

    private ArrayList<VideoInfoBean> mList = new ArrayList<>();

    private int beginItem = 0;

    private String curSearchText = "";

    private int lastVisibleItem;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public int getRootViewId() {
        return R.layout.activity_search;
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
        initVisiblites();
        initAdapters();
        initListeners();
    }

    private void initVisiblites() {
        mTvCancel.setVisibility(View.VISIBLE);
    }

    private void initAdapters() {
        if (null == mList) {
            return;
        }
        mAdapter = new AbstractSimpleAdapter<VideoInfoBean>(mContext, mList, R.layout.item_simple_tv) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, VideoInfoBean data) {
                if (null != data) {
                    holder.<TextView>getView(R.id.tv_item_simple).setText(data.getVideoName());
                }

            }
        };
    }

    private void initListeners() {
        if (null == mAdapter) {
            return;
        }

        //为RecyclerView设置Adapter
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRcvSearch.setNestedScrollingEnabled(false);
        mRcvSearch.setLayoutManager(layoutManager);
        mRcvSearch.setAdapter(mAdapter);
        mRcvSearch.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        ) {

                    showLoading("搜索中...");
                    String keyWords = mEditSearch.getText().toString();
                    beginItem = 0;
                    curSearchText = keyWords;
                    presenter.searchVideoByKeywords(keyWords, beginItem, 20);

                }

                return false;
            }
        });
        mRcvSearch.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                mEditSearch.setFocusable(false);
                mEditSearch.clearFocus();
                return false;
            }
        });

        mAdapter.setOnItemClickListener(new AbstractSimpleAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(Object o, int position) {
                try {
                    VideoInfoBean bean = (VideoInfoBean) o;
                    if (null == bean) {
                        return;
                    }
                    Intent intent = new Intent(SearchActivity.this, PlayVideoActivity.class);
                    intent.putExtra("videoInfoBean", bean);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        mRcvSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_SETTLING && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    showLoading();
                    beginItem += 20;
                    presenter.searchVideoByKeywords(curSearchText, beginItem, 20);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

    }

    private void loadMoreData() {

    }

    @OnClick({R.id.tvCancel, R.id.editSearch})
    public void viewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                InputMethodUtils.hide(mContext);
                finish();
                break;
            case R.id.editSearch:
                showSoftInputFromEditText(mEditSearch);
                break;
            default:
                break;
        }
    }

    private void showSoftInputFromEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodUtils.show(mContext, editText);
    }

    @Override
    public void searchVideoSucceed(ArrayList<VideoInfoBean> list) {
        if (Constants.isUserCancelDialog) {
            return;
        }
        if (null == list || 0 == list.size() || null == mList) {
            dismissLoading();
            Log.e(TAG, "searchVideoSucceed: 返回的数据格式有误或者为空！");
        }
        if (null == mList) {
            mList = new ArrayList<>();
        }
        mList.clear();
        mList.addAll(list);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                int size = mAdapter.getItemCount();
                if (null == mAdapter || 0 == size) {
                    initAdapters();
                }
                mAdapter.notifyDataSetChanged();
                dismissLoading();
            }
        });

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DismissDialogEvent event) {
        if (event.isRefresh()) {
            Constants.isUserCancelDialog = true;
            dismissLoading();
        }
    }

    @Override
    public void onBackPressed() {
        if (isDialogShowing()) {
            Constants.isUserCancelDialog=true;
            dismissLoading();
        }
        super.onBackPressed();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void complete() {
        dismissLoading();
    }
}
