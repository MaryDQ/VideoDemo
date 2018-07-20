// Generated code from Butter Knife. Do not modify!
package com.microsys.videodemo.ui.activities.location;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.baidu.mapapi.map.MapView;
import com.microsys.videodemo.R;
import com.microsys.videodemo.base.BaseActivity_ViewBinding;
import com.microsys.videodemo.widget.MyRecyclerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LocationActivity_ViewBinding extends BaseActivity_ViewBinding {
  private LocationActivity target;

  private View view2131230883;

  @UiThread
  public LocationActivity_ViewBinding(LocationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LocationActivity_ViewBinding(final LocationActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mapView = Utils.findRequiredViewAsType(source, R.id.mapView, "field 'mapView'", MapView.class);
    target.tvLeft = Utils.findRequiredViewAsType(source, R.id.tvLeft, "field 'tvLeft'", TextView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tvRight, "field 'tvRight'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_request_location, "field 'mIvRequestLocation' and method 'onViewClicked'");
    target.mIvRequestLocation = Utils.castView(view, R.id.iv_request_location, "field 'mIvRequestLocation'", ImageView.class);
    view2131230883 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mRcvLiveVideoDetail = Utils.findRequiredViewAsType(source, R.id.rcv_live_video_detail, "field 'mRcvLiveVideoDetail'", MyRecyclerView.class);
  }

  @Override
  public void unbind() {
    LocationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mapView = null;
    target.tvLeft = null;
    target.tvTitle = null;
    target.tvRight = null;
    target.mIvRequestLocation = null;
    target.mRcvLiveVideoDetail = null;

    view2131230883.setOnClickListener(null);
    view2131230883 = null;

    super.unbind();
  }
}
