package com.microsys.videodemo.ui.presenter;

import com.microsys.videodemo.api.Api;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GetRecommendListPresenter_Factory implements Factory<GetRecommendListPresenter> {
  private final Provider<Api> apiProvider;

  public GetRecommendListPresenter_Factory(Provider<Api> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public GetRecommendListPresenter get() {
    return new GetRecommendListPresenter(apiProvider.get());
  }

  public static GetRecommendListPresenter_Factory create(Provider<Api> apiProvider) {
    return new GetRecommendListPresenter_Factory(apiProvider);
  }
}
