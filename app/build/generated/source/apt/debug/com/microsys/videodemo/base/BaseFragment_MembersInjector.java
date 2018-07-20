package com.microsys.videodemo.base;

import com.microsys.videodemo.base.mvp.IPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BaseFragment_MembersInjector<P extends IPresenter>
    implements MembersInjector<BaseFragment<P>> {
  private final Provider<P> presenterProvider;

  public BaseFragment_MembersInjector(Provider<P> presenterProvider) {
    this.presenterProvider = presenterProvider;
  }

  public static <P extends IPresenter> MembersInjector<BaseFragment<P>> create(
      Provider<P> presenterProvider) {
    return new BaseFragment_MembersInjector<P>(presenterProvider);
  }

  @Override
  public void injectMembers(BaseFragment<P> instance) {
    injectPresenter(instance, presenterProvider.get());
  }

  public static <P extends IPresenter> void injectPresenter(BaseFragment<P> instance, P presenter) {
    instance.presenter = presenter;
  }
}
