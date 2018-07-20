package com.microsys.videodemo.base;

import com.microsys.videodemo.base.mvp.IPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BaseLazyFragment_MembersInjector<P extends IPresenter>
    implements MembersInjector<BaseLazyFragment<P>> {
  private final Provider<P> presenterProvider;

  public BaseLazyFragment_MembersInjector(Provider<P> presenterProvider) {
    this.presenterProvider = presenterProvider;
  }

  public static <P extends IPresenter> MembersInjector<BaseLazyFragment<P>> create(
      Provider<P> presenterProvider) {
    return new BaseLazyFragment_MembersInjector<P>(presenterProvider);
  }

  @Override
  public void injectMembers(BaseLazyFragment<P> instance) {
    injectPresenter(instance, presenterProvider.get());
  }

  public static <P extends IPresenter> void injectPresenter(
      BaseLazyFragment<P> instance, P presenter) {
    instance.presenter = presenter;
  }
}
