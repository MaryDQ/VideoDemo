package com.microsys.videodemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.microsys.videodemo.app.MyApplication;
import com.microsys.videodemo.base.mvp.IPresenter;
import com.microsys.videodemo.di.component.AppComponent;
import com.microsys.videodemo.utils.L;

import java.lang.reflect.Field;

import javax.inject.Inject;

public abstract class BaseLazyFragment<P extends IPresenter>  extends Fragment{
    protected LayoutInflater inflater;
    private View contentView;
    private Context mContext;
    private ViewGroup container;

    @Inject
    protected P presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    //子类通过重写onCreateView，调用setOnContentView进行布局设置，否则contentView==null，返回null
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);
        if (contentView == null)
        { return super.onCreateView(inflater, container, savedInstanceState);}
        setupActivityComponent(MyApplication.getInstance().getAppComponent());
        if(presenter != null) {
            presenter.attachView(this);
        }
        return contentView;
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);

    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
        container = null;
        inflater = null;
    }

    public Context getApplicationContext() {
        return mContext;
    }

    public void setContentView(int layoutResID) {
        setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
    }

    public void setContentView(View view) {
        contentView = view;
    }

    public View getContentView() {
        return contentView;
    }

    public View findViewById(int id) {
        if (contentView != null)
        {return contentView.findViewById(id);}
        return null;
    }

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        L.d("TAG", "onDetach() : ");
        super.onDetach();
        if(presenter != null){
            presenter.detachView();
        }
        this.presenter = null;
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onDestroy() {
        L.d("TAG", "onDestroy() : ");
        super.onDestroy();
    }
}
