// Generated code from Butter Knife. Do not modify!
package com.microsys.videodemo.ui.activities.category;

import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.microsys.videodemo.R;
import com.microsys.videodemo.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CategoryActivity_ViewBinding extends BaseActivity_ViewBinding {
  private CategoryActivity target;

  private View view2131231052;

  private View view2131231043;

  private View view2131231042;

  @UiThread
  public CategoryActivity_ViewBinding(CategoryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CategoryActivity_ViewBinding(final CategoryActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tv_jump_to_map, "field 'mTvJumpToMap' and method 'viewClicked'");
    target.mTvJumpToMap = Utils.castView(view, R.id.tv_jump_to_map, "field 'mTvJumpToMap'", TextView.class);
    view2131231052 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tvRight, "field 'mTvRight' and method 'viewClicked'");
    target.mTvRight = Utils.castView(view, R.id.tvRight, "field 'mTvRight'", TextView.class);
    view2131231043 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewClicked(p0);
      }
    });
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.rcv_category, "field 'mRecyclerView'", RecyclerView.class);
    target.mSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.srl_category, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.tvLeft, "method 'viewClicked'");
    view2131231042 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewClicked(p0);
      }
    });
  }

  @Override
  public void unbind() {
    CategoryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvJumpToMap = null;
    target.mTvRight = null;
    target.mRecyclerView = null;
    target.mSwipeRefreshLayout = null;

    view2131231052.setOnClickListener(null);
    view2131231052 = null;
    view2131231043.setOnClickListener(null);
    view2131231043 = null;
    view2131231042.setOnClickListener(null);
    view2131231042 = null;

    super.unbind();
  }
}
