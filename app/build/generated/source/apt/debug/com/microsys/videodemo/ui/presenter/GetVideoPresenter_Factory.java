package com.microsys.videodemo.ui.presenter;

import com.microsys.videodemo.api.Api;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GetVideoPresenter_Factory implements Factory<GetVideoPresenter> {
  private final Provider<Api> apiProvider;

  public GetVideoPresenter_Factory(Provider<Api> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public GetVideoPresenter get() {
    return new GetVideoPresenter(apiProvider.get());
  }

  public static GetVideoPresenter_Factory create(Provider<Api> apiProvider) {
    return new GetVideoPresenter_Factory(apiProvider);
  }
}
