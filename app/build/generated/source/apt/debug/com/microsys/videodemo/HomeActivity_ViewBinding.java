// Generated code from Butter Knife. Do not modify!
package com.microsys.videodemo;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import net.lucode.hackware.magicindicator.MagicIndicator;

public class HomeActivity_ViewBinding implements Unbinder {
  private HomeActivity target;

  private View view2131230879;

  private View view2131230881;

  private View view2131230880;

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HomeActivity_ViewBinding(final HomeActivity target, View source) {
    this.target = target;

    View view;
    target.mainIndicator = Utils.findRequiredViewAsType(source, R.id.main_indicator, "field 'mainIndicator'", MagicIndicator.class);
    view = Utils.findRequiredView(source, R.id.ivBack, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = Utils.castView(view, R.id.ivBack, "field 'ivBack'", ImageView.class);
    view2131230879 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ivSearch, "field 'ivSearch' and method 'onViewClicked'");
    target.ivSearch = Utils.castView(view, R.id.ivSearch, "field 'ivSearch'", ImageView.class);
    view2131230881 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ivCategory, "field 'ivCategory' and method 'onViewClicked'");
    target.ivCategory = Utils.castView(view, R.id.ivCategory, "field 'ivCategory'", ImageView.class);
    view2131230880 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mainIndicator = null;
    target.ivBack = null;
    target.ivSearch = null;
    target.ivCategory = null;

    view2131230879.setOnClickListener(null);
    view2131230879 = null;
    view2131230881.setOnClickListener(null);
    view2131230881 = null;
    view2131230880.setOnClickListener(null);
    view2131230880 = null;
  }
}
