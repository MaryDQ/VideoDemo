package com.microsys.videodemo.ui.activities.location;

import com.microsys.videodemo.base.BaseActivity_MembersInjector;
import com.microsys.videodemo.ui.presenter.GetAllLocationSimpleInfoPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LocationActivity_MembersInjector implements MembersInjector<LocationActivity> {
  private final Provider<GetAllLocationSimpleInfoPresenter> presenterProvider;

  public LocationActivity_MembersInjector(
      Provider<GetAllLocationSimpleInfoPresenter> presenterProvider) {
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<LocationActivity> create(
      Provider<GetAllLocationSimpleInfoPresenter> presenterProvider) {
    return new LocationActivity_MembersInjector(presenterProvider);
  }

  @Override
  public void injectMembers(LocationActivity instance) {
    BaseActivity_MembersInjector.injectPresenter(instance, presenterProvider.get());
  }
}
