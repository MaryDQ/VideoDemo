// Generated code from Butter Knife. Do not modify!
package com.microsys.videodemo.ui.activities.media.video;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.microsys.videodemo.R;
import com.microsys.videodemo.base.BaseActivity_ViewBinding;
import com.microsys.videodemo.widget.LandLayoutVideo;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PlayVideoActivity_ViewBinding extends BaseActivity_ViewBinding {
  private PlayVideoActivity target;

  private View view2131231042;

  @UiThread
  public PlayVideoActivity_ViewBinding(PlayVideoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PlayVideoActivity_ViewBinding(final PlayVideoActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tvRight, "field 'tvRight'", TextView.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.rcv_play_video, "field 'mRecyclerView'", RecyclerView.class);
    target.mPlayer = Utils.findRequiredViewAsType(source, R.id.rtsp_video, "field 'mPlayer'", LandLayoutVideo.class);
    view = Utils.findRequiredView(source, R.id.tvLeft, "method 'onViewClicked'");
    view2131231042 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  public void unbind() {
    PlayVideoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvRight = null;
    target.mRecyclerView = null;
    target.mPlayer = null;

    view2131231042.setOnClickListener(null);
    view2131231042 = null;

    super.unbind();
  }
}
