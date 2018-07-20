package com.microsys.videodemo.ui.activities.location;

import android.util.SparseArray;

import com.microsys.videodemo.ui.bean.video_info_beans.SimpleVideoInfoBean;

import java.util.ArrayList;

public class LiveVideoRepo {

    private static volatile LiveVideoRepo mLiveVideoRepo;
    private final SparseArray<SimpleVideoInfoBean> mLatLngRepo = new SparseArray<>();

    public static LiveVideoRepo getInstance() {
        if (null == mLiveVideoRepo) {
            synchronized (LiveVideoRepo.class) {
                if (null == mLiveVideoRepo) {
                    mLiveVideoRepo = new LiveVideoRepo();
                }
            }
        }
        return mLiveVideoRepo;
    }

    public void addLatLngBean(SimpleVideoInfoBean beans) {
        if (null == beans) {
            return;
        }
        int id = Integer.getInteger(beans.getVideoID());
        mLatLngRepo.append(id, beans);
    }

    public void addLatLngBeans(ArrayList<SimpleVideoInfoBean> beansList) {
        if (null == beansList || beansList.size() == 0) {
            return;
        }
        for (SimpleVideoInfoBean bean : beansList) {
            int id = Integer.getInteger(bean.getVideoID());
            mLatLngRepo.append(id, bean);
        }
    }

    public ArrayList<SimpleVideoInfoBean> getVideoInfoBeans() {

        ArrayList<SimpleVideoInfoBean> list = new ArrayList<>();

        for (int i = 0; i < mLatLngRepo.size(); i++) {
            list.add(mLatLngRepo.get(i));
        }
        return list;
    }

    public SimpleVideoInfoBean getVideoInfoBeanById(int id) {
        SimpleVideoInfoBean bean = mLatLngRepo.get(id);
        return bean;
    }


    public void clear() {
        mLatLngRepo.clear();
    }
}
