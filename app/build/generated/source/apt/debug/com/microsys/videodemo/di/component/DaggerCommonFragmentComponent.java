package com.microsys.videodemo.di.component;

import com.microsys.videodemo.base.BaseFragment_MembersInjector;
import com.microsys.videodemo.ui.fragments.VideoFragment;
import com.microsys.videodemo.ui.presenter.GetVideoPresenter;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerCommonFragmentComponent implements CommonFragmentComponent {
  private AppComponent appComponent;

  private DaggerCommonFragmentComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  private GetVideoPresenter getGetVideoPresenter() {
    return new GetVideoPresenter(
        Preconditions.checkNotNull(
            appComponent.api(), "Cannot return null from a non-@Nullable component method"));
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.appComponent = builder.appComponent;
  }

  @Override
  public VideoFragment inject(VideoFragment fragment) {
    return injectVideoFragment(fragment);
  }

  private VideoFragment injectVideoFragment(VideoFragment instance) {
    BaseFragment_MembersInjector.injectPresenter(instance, getGetVideoPresenter());
    return instance;
  }

  public static final class Builder {
    private AppComponent appComponent;

    private Builder() {}

    public CommonFragmentComponent build() {
      if (appComponent == null) {
        throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
      }
      return new DaggerCommonFragmentComponent(this);
    }

    public Builder appComponent(AppComponent appComponent) {
      this.appComponent = Preconditions.checkNotNull(appComponent);
      return this;
    }
  }
}
