package com.microsys.videodemo.ui.activities.location;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.microsys.videodemo.R;
import com.microsys.videodemo.base.BaseActivity;
import com.microsys.videodemo.common.Constants;
import com.microsys.videodemo.di.component.AppComponent;
import com.microsys.videodemo.di.component.DaggerCommonActivityComponent;
import com.microsys.videodemo.eventbus_events.DismissDialogEvent;
import com.microsys.videodemo.ui.activities.media.video.PlayVideoActivity;
import com.microsys.videodemo.ui.adapter.LiveVideoDetailAdapter;
import com.microsys.videodemo.ui.bean.video_info_beans.SimpleVideoInfoBean;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoID;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;
import com.microsys.videodemo.ui.contract.GetAllLocationContract;
import com.microsys.videodemo.ui.presenter.GetAllLocationSimpleInfoPresenter;
import com.microsys.videodemo.utils.StringUtil;
import com.microsys.videodemo.utils.ToastUtils;
import com.microsys.videodemo.utils.baiduUtils.clusterutil.clustering.Cluster;
import com.microsys.videodemo.utils.baiduUtils.clusterutil.clustering.ClusterItem;
import com.microsys.videodemo.utils.baiduUtils.clusterutil.clustering.ClusterManager;
import com.microsys.videodemo.widget.MyRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LocationActivity extends BaseActivity<GetAllLocationSimpleInfoPresenter> implements SensorEventListener, BaiduMap.OnMapLoadedCallback, GetAllLocationContract.View {

    public MyLocationListener myListener = new MyLocationListener();

    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.tvLeft)
    TextView tvLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.iv_request_location)
    ImageView mIvRequestLocation;
    @BindView(R.id.rcv_live_video_detail)
    MyRecyclerView mRcvLiveVideoDetail;

    LocationClient mLocClient;
    // UI相关
    boolean isFirstLoc = true; // 是否首次定位
    private SensorManager mSensorManager;
    //    private static final int accuracyCircleFillColor = 0xAAFF8888;
//    private static final int accuracyCircleStrokeColor = 0xAA0000FF;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private BaiduMap mBaiduMap;
    private MyLocationData locData;
    private float direction;


    //直播视频列表adapter
    private LiveVideoDetailAdapter mAdapter;

    private ArrayList<VideoInfoBean> mRcvList = new ArrayList<>();

    private boolean isRcvShowing = false;

    private MapStatus ms;//地图状态
    private ClusterManager<MyItem> mClusterManager;

    private android.os.Handler mHandler = new android.os.Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private BitmapDescriptor mIconVideo = BitmapDescriptorFactory.fromResource(R.mipmap.icon_video);

    @Override
    public void onMapLoaded() {

        ms = new MapStatus.Builder().zoom(13).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));

        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap);

//        addMarker();

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                showLoading();
                isRcvShowing = true;
                ArrayList<VideoID> list = new ArrayList<>();
                Iterator<MyItem> iterator = cluster.getItems().iterator();
                while (iterator.hasNext()) {
                    list.add(new VideoID(iterator.next().getVideoId()));
                }
                presenter.getVideoByIds(list);
                requestLocation(cluster.getPosition().latitude, cluster.getPosition().longitude);
                mRcvLiveVideoDetail.setVisibility(View.VISIBLE);

                return false;
            }
        });

        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
                showLoading();
                isRcvShowing = true;
                ArrayList<VideoID> list = new ArrayList<>();
                list.add(new VideoID(item.getVideoId()));
                presenter.getVideoByIds(list);
                requestLocation(item.getPosition().latitude, item.getPosition().longitude);
                mRcvLiveVideoDetail.setVisibility(View.VISIBLE);
                return false;
            }
        });

        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        // 设置maker点击时的响应
        mBaiduMap.setOnMarkerClickListener(mClusterManager);

    }

    /**
     * 地图定位到指定的经纬度
     *
     * @param currentLat 经度
     * @param currentLon 维度
     */
    private void requestLocation(double currentLat, double currentLon) {
        //settings
        LatLng ll = new LatLng(currentLat,
                currentLon);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    public void onBackPressed() {
        if (isDialogShowing()) {
            Constants.isUserCancelDialog = true;
            dismissLoading();
        }
        this.finish();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DismissDialogEvent event) {
        if (event.isRefresh()) {
            dismissLoading();
        }
    }





    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;

        if (null != mIconVideo) {
            mIconVideo.recycle();
        }
        super.onDestroy();
    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_location;
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

        initAdapters();
        initBaiduMap();
        initAllVideos();
    }

    private void initAdapters() {
        mAdapter = new LiveVideoDetailAdapter(mRcvList, mContext);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);

        mRcvLiveVideoDetail.setLayoutManager(layoutManager);
        mRcvLiveVideoDetail.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRcvLiveVideoDetail.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new LiveVideoDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    VideoInfoBean bean = mRcvList.get(position);
                    Intent intent = new Intent(LocationActivity.this, PlayVideoActivity.class);
                    intent.putExtra("videoInfoBean", bean);
                    intent.putExtra("fromLocationAct", true);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initBaiduMap() {
        //获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mCurrentMode = LocationMode.NORMAL;

        //隐藏缩放的按钮
        mapView.showZoomControls(false);

        View child = mapView.getChildAt(1);
        boolean isExistMapLogo = child != null
                && (child instanceof ImageView || child instanceof ZoomControls);
        if (isExistMapLogo) {
            child.setVisibility(View.INVISIBLE);
        }


        // 地图初始化
        mBaiduMap = mapView.getMap();

        mLocClient = new LocationClient(this);
        myListener = new MyLocationListener();
        mLocClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        // 打开gps
        option.setOpenGps(true);
        // 设置坐标类型
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(12.0f);
        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.animateMapStatus(msu);


        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (isRcvShowing) {
                    mRcvLiveVideoDetail.setVisibility(View.GONE);
                    isRcvShowing = false;
                }
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });


    }

    private void initAllVideos() {
        showLoading(getString(R.string.loading));
        presenter.getAllLocationSimpleInfo();
    }

    @Override
    protected void onStart() {
        // 开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocClient.isStarted()) {
            mLocClient.start();
        }

        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocClient.stop();


        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @OnClick({R.id.iv_request_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_request_location:
                LatLng ll = new LatLng(mCurrentLat,
                        mCurrentLon);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                break;
            default:
                break;
        }
    }

    @Override
    public void getLocationInfoSucceed(List<SimpleVideoInfoBean> list) {
        if (Constants.isUserCancelDialog) {
            return;
        }
        if (null == list || list.size() == 0) {
            return;
        }
        addMarkers(list);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
            }
        });
    }

    private void addMarkers(List<SimpleVideoInfoBean> videoInfoBeans) {
        if (null == videoInfoBeans) {
            return;
        }
        List<MyItem> items = new ArrayList<MyItem>();
        ArrayList<SimpleVideoInfoBean> tempList = new ArrayList<>();
        tempList.addAll(videoInfoBeans);
        for (SimpleVideoInfoBean beans : tempList) {
            try {
                if (StringUtil.isEmpty(beans.getPos_x()) || StringUtil.isEmpty(beans.getPos_y())) {
                    continue;
                }
                MyItem item = new MyItem(beans);
                items.add(item);
            } catch (NumberFormatException e) {
                dismissLoading();
                e.printStackTrace();
            }

        }
        final List<MyItem> list = items;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mClusterManager.addItems(list);
            }
        });
    }

    /**
     * 获取数据成功，刷新列表
     *
     * @param list 重新获取的数据列表
     */
    @Override
    public void getVideoByIdsSucceed(ArrayList<VideoInfoBean> list) {
        if (Constants.isUserCancelDialog) {
            return;
        }

        if (null == list) {
            return;
        }
        if (list.size() > 0) {
            mRcvList = new ArrayList<>();
            mRcvList.addAll(list);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    initAdapters();
                    dismissLoading();
                }
            });

        }
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void complete() {

    }

    /**
     * 每个Marker点，包含Marker点坐标以及图标
     */
    public class MyItem implements ClusterItem {
        private LatLng mPosition = null;

        private String videoId;

        private SimpleVideoInfoBean bean;

        public MyItem(SimpleVideoInfoBean bean) {

            this.bean = bean;
            try {
                Double latitude = Double.valueOf(bean.getPos_y());
                Double longtitude = Double.valueOf(bean.getPos_x());
                mPosition = new LatLng(latitude, longtitude);
                this.videoId = bean.getVideoID();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        public String getVideoId() {
            return videoId == null ? "" : videoId;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public BitmapDescriptor getBitmapDescriptor() {
            return mIconVideo;
        }
    }

    class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            mCurrentLat = location.getLatitude();    //获取纬度信息
            mCurrentLon = location.getLongitude();    //获取经度信息
            mCurrentAccracy = location.getRadius();    //获取定位精度，默认值为0.0f

            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }



}
