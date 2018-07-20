package com.microsys.videodemo.base;

import com.microsys.videodemo.base.mvp.IPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BaseActivity_MembersInjector<P extends IPresenter>
    implements MembersInjector<BaseActivity<P>> {
  private final Provider<P> presenterProvider;

  public BaseActivity_MembersInjector(Provider<P> presenterProvider) {
    this.presenterProvider = presenterProvider;
  }

  public static <P extends IPresenter> MembersInjector<BaseActivity<P>> create(
      Provider<P> presenterProvider) {
    return new BaseActivity_MembersInjector<P>(presenterProvider);
  }

  @Override
  public void injectMembers(BaseActivity<P> instance) {
    injectPresenter(instance, presenterProvider.get());
  }

  public static <P extends IPresenter> void injectPresenter(BaseActivity<P> instance, P presenter) {
    instance.presenter = presenter;
  }
}
