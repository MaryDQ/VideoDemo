// Generated code from Butter Knife. Do not modify!
package com.microsys.videodemo.ui.activities.search;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.microsys.videodemo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchActivity_ViewBinding implements Unbinder {
  private SearchActivity target;

  private View view2131230810;

  private View view2131231041;

  @UiThread
  public SearchActivity_ViewBinding(SearchActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchActivity_ViewBinding(final SearchActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.editSearch, "field 'mEditSearch' and method 'viewClicked'");
    target.mEditSearch = Utils.castView(view, R.id.editSearch, "field 'mEditSearch'", EditText.class);
    view2131230810 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tvCancel, "field 'mTvCancel' and method 'viewClicked'");
    target.mTvCancel = Utils.castView(view, R.id.tvCancel, "field 'mTvCancel'", TextView.class);
    view2131231041 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewClicked(p0);
      }
    });
    target.mRcvSearch = Utils.findRequiredViewAsType(source, R.id.rcv_search, "field 'mRcvSearch'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEditSearch = null;
    target.mTvCancel = null;
    target.mRcvSearch = null;

    view2131230810.setOnClickListener(null);
    view2131230810 = null;
    view2131231041.setOnClickListener(null);
    view2131231041 = null;
  }
}
