package com.microsys.videodemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsys.videodemo.app.MyApplication;
import com.microsys.videodemo.base.BaseNoTitleActivity;
import com.microsys.videodemo.common.Constants;
import com.microsys.videodemo.di.component.AppComponent;
import com.microsys.videodemo.di.component.DaggerCommonActivityComponent;
import com.microsys.videodemo.eventbus_events.DismissDialogEvent;
import com.microsys.videodemo.eventbus_events.HomeActivityRefreshEvent;
import com.microsys.videodemo.eventbus_events.HomeActivitySwitchFragEvent;
import com.microsys.videodemo.ui.activities.category.CategoryActivity;
import com.microsys.videodemo.ui.activities.search.SearchActivity;
import com.microsys.videodemo.ui.bean.video_info_beans.GroupOrVideoBean;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;
import com.microsys.videodemo.ui.contract.GetRecommendListContract;
import com.microsys.videodemo.ui.fragments.VideoFragment;
import com.microsys.videodemo.ui.presenter.GetRecommendListPresenter;
import com.microsys.videodemo.utils.GetFileUtils;
import com.microsys.videodemo.utils.ThreadUtils;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author mlx
 * @date 0609 2018
 */
public class HomeActivity extends BaseNoTitleActivity<GetRecommendListPresenter> implements GetRecommendListContract.View {

    @BindView(R.id.main_indicator)
    MagicIndicator mainIndicator;
    //    @BindView(R.id.main_viewPager)
//    NoScrollViewPager mainViewPager;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.ivCategory)
    ImageView ivCategory;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private List<String> tabList = new ArrayList<>();
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
    private int curFragmentItem = 0;
    /**
     * group分组
     */
    private List<GroupOrVideoBean.GroupsBean> mGroupList = new ArrayList<>();

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
    private Fragment curFragment;

    @Override
    public int getRootViewId() {
        return R.layout.activity_main;
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
        ivBack.setVisibility(View.GONE);

//        requestLocalData();
        requestData();


    }

    private void requestLocalData() {
        ThreadUtils.doSingleThread(new Runnable() {
            @Override
            public void run() {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/VideoDemo/json.txt";
                String result = GetFileUtils.readTxtFile(path).trim();
                try {
                    JSONObject object=new JSONObject(result);

                    String category=object.getString("category");
                    String[] categoryArray=category.split(",");
                    fragments.clear();
                    tabList.clear();
                    for (int i = 0; i < categoryArray.length; i++) {
                        String jsonItem=object.getString(categoryArray[i]);
                        List<VideoInfoBean> list=new Gson().fromJson(jsonItem,new TypeToken<List<VideoInfoBean>>(){}.getType());
                        VideoFragment videoFragment = new VideoFragment(list,true);

                        fragments.add(i, videoFragment);
                        tabList.add(categoryArray[i]);
                    }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            initIndicator();//Indicator初始化
                            mFragmentContainerHelper.handlePageSelected(0, false);
                            switchPages(0);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void requestData() {
        presenter.getRecommendList();
    }

    @OnClick({R.id.ivBack, R.id.ivSearch, R.id.ivCategory})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivBack:

                break;
            case R.id.ivSearch:
                intent = new Intent(mContext, SearchActivity.class);
                startActivity(intent);

                break;
            case R.id.ivCategory:
                if (null == mGroupList || 0 == mGroupList.size()) {
                    return;
                }
                intent = new Intent(mContext, CategoryActivity.class);
                intent.putExtra("fromHome", "HomeActivity");
                intent.putExtra("selectedItem", curFragmentItem);
                intent.putExtra("groupID", mGroupList.get(curFragmentItem).getGroupID());
                intent.putExtra("groupName", mGroupList.get(curFragmentItem).getGroupName());
                startActivityForResult(intent, 0x0001);

                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x0001:
                    try {

                        Bundle bundle = data.getExtras();
                        int curItem = bundle.getInt("selectedItem");
                        String groupId = bundle.getString("groupID");
                        String groupName = bundle.getString("groupName");

                        VideoFragment fragment = (VideoFragment) fragments.get(curItem);

                        fragment.refreshByGroupId(groupId, groupName);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (isDialogShowing()) {
            dismissLoading();
            Constants.isUserCancelDialog = true;
        }

        MyApplication.getInstance().exitApp();
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
    public void onMessageEvent(HomeActivityRefreshEvent event) {
        if (event.isRefresh()) {
            dismissLoading();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DismissDialogEvent event) {
        if (event.isRefresh()) {
            dismissLoading();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(HomeActivitySwitchFragEvent event) {
        if (event.isRefresh()) {
            if (tabList == null || event.getItem() < 0 || event.getItem() > tabList.size() - 1) {
                return;
            }
            mFragmentContainerHelper.handlePageSelected(event.getItem());
            switchPages(event.getItem());
        }
    }

    private void switchPages(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        for (int i = 0, j = fragments.size(); i < j; i++) {
            if (i == index) {
                continue;
            }
            fragment = fragments.get(i);
            if (fragment.isAdded()) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragment = fragments.get(index);
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
        curFragmentItem = index;
        Constants.CUR_FRAGMENT_ITEM = index;
    }

    @Override
    public void getRecommendListSucceed(List<GroupOrVideoBean.GroupsBean> list) {
        showLoading();
        initFragment(list);
        dismissLoading();

    }

    private void initFragment(List<GroupOrVideoBean.GroupsBean> groupsList) {
        if (null == groupsList || null == fragments || null == tabList) {
            return;
        }
        mGroupList = groupsList;
        fragments.clear();
        tabList.clear();
        for (int i = 0; i < groupsList.size(); i++) {

            GroupOrVideoBean.GroupsBean bean = groupsList.get(i);
            VideoFragment videoFragment = new VideoFragment(bean,false);

            fragments.add(i, videoFragment);
            tabList.add(groupsList.get(i).getGroupName());
        }
        initIndicator();//Indicator初始化
        mFragmentContainerHelper.handlePageSelected(0, false);
        switchPages(0);
    }

    private void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return fragments == null ? 0 : fragments.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(tabList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mFragmentContainerHelper.handlePageSelected(index);
                        switchPages(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        mainIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(mainIndicator);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void complete() {

    }


}
