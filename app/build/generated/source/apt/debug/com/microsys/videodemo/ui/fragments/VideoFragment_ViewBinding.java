// Generated code from Butter Knife. Do not modify!
package com.microsys.videodemo.ui.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.microsys.videodemo.R;
import com.youth.banner.Banner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VideoFragment_ViewBinding implements Unbinder {
  private VideoFragment target;

  @UiThread
  public VideoFragment_ViewBinding(VideoFragment target, View source) {
    this.target = target;

    target.banner = Utils.findRequiredViewAsType(source, R.id.banner, "field 'banner'", Banner.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.rcv_fragment, "field 'mRecyclerView'", RecyclerView.class);
    target.mSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.srl_video_fragment, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.mNestedScrollView = Utils.findRequiredViewAsType(source, R.id.nsv_video_fragment, "field 'mNestedScrollView'", NestedScrollView.class);
    target.mTvCurShowing = Utils.findRequiredViewAsType(source, R.id.tv_videofrag_cur_showing, "field 'mTvCurShowing'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    VideoFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.banner = null;
    target.mRecyclerView = null;
    target.mSwipeRefreshLayout = null;
    target.mNestedScrollView = null;
    target.mTvCurShowing = null;
  }
}
