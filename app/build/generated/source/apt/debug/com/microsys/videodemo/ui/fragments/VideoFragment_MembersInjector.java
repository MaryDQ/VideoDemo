package com.microsys.videodemo.ui.fragments;

import com.microsys.videodemo.base.BaseFragment_MembersInjector;
import com.microsys.videodemo.ui.presenter.GetVideoPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class VideoFragment_MembersInjector implements MembersInjector<VideoFragment> {
  private final Provider<GetVideoPresenter> presenterProvider;

  public VideoFragment_MembersInjector(Provider<GetVideoPresenter> presenterProvider) {
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<VideoFragment> create(
      Provider<GetVideoPresenter> presenterProvider) {
    return new VideoFragment_MembersInjector(presenterProvider);
  }

  @Override
  public void injectMembers(VideoFragment instance) {
    BaseFragment_MembersInjector.injectPresenter(instance, presenterProvider.get());
  }
}
