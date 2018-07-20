package com.microsys.videodemo;

import com.microsys.videodemo.base.BaseNoTitleActivity_MembersInjector;
import com.microsys.videodemo.ui.presenter.GetRecommendListPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HomeActivity_MembersInjector implements MembersInjector<HomeActivity> {
  private final Provider<GetRecommendListPresenter> presenterProvider;

  public HomeActivity_MembersInjector(Provider<GetRecommendListPresenter> presenterProvider) {
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<HomeActivity> create(
      Provider<GetRecommendListPresenter> presenterProvider) {
    return new HomeActivity_MembersInjector(presenterProvider);
  }

  @Override
  public void injectMembers(HomeActivity instance) {
    BaseNoTitleActivity_MembersInjector.injectPresenter(instance, presenterProvider.get());
  }
}
