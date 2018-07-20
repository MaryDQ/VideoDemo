// Generated code from Butter Knife. Do not modify!
package com.microsys.videodemo.base;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.microsys.videodemo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseActivity_ViewBinding implements Unbinder {
  private BaseActivity target;

  private View view2131231042;

  @UiThread
  public BaseActivity_ViewBinding(BaseActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BaseActivity_ViewBinding(final BaseActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tvLeft, "method 'goBack'");
    view2131231042 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.goBack(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;

    view2131231042.setOnClickListener(null);
    view2131231042 = null;
  }
}
