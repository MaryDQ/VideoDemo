package com.microsys.videodemo.base;

import com.microsys.videodemo.base.mvp.IPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BaseNoTitleActivity_MembersInjector<P extends IPresenter>
    implements MembersInjector<BaseNoTitleActivity<P>> {
  private final Provider<P> presenterProvider;

  public BaseNoTitleActivity_MembersInjector(Provider<P> presenterProvider) {
    this.presenterProvider = presenterProvider;
  }

  public static <P extends IPresenter> MembersInjector<BaseNoTitleActivity<P>> create(
      Provider<P> presenterProvider) {
    return new BaseNoTitleActivity_MembersInjector<P>(presenterProvider);
  }

  @Override
  public void injectMembers(BaseNoTitleActivity<P> instance) {
    injectPresenter(instance, presenterProvider.get());
  }

  public static <P extends IPresenter> void injectPresenter(
      BaseNoTitleActivity<P> instance, P presenter) {
    instance.presenter = presenter;
  }
}
