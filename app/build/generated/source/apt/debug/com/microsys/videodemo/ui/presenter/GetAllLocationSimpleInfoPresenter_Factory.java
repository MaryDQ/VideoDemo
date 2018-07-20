package com.microsys.videodemo.ui.presenter;

import com.microsys.videodemo.api.Api;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GetAllLocationSimpleInfoPresenter_Factory
    implements Factory<GetAllLocationSimpleInfoPresenter> {
  private final Provider<Api> apiProvider;

  public GetAllLocationSimpleInfoPresenter_Factory(Provider<Api> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public GetAllLocationSimpleInfoPresenter get() {
    return new GetAllLocationSimpleInfoPresenter(apiProvider.get());
  }

  public static GetAllLocationSimpleInfoPresenter_Factory create(Provider<Api> apiProvider) {
    return new GetAllLocationSimpleInfoPresenter_Factory(apiProvider);
  }
}
