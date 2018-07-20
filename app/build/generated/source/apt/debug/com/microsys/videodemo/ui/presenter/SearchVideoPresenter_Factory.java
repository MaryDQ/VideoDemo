package com.microsys.videodemo.ui.presenter;

import com.microsys.videodemo.api.Api;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchVideoPresenter_Factory implements Factory<SearchVideoPresenter> {
  private final Provider<Api> apiProvider;

  public SearchVideoPresenter_Factory(Provider<Api> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public SearchVideoPresenter get() {
    return new SearchVideoPresenter(apiProvider.get());
  }

  public static SearchVideoPresenter_Factory create(Provider<Api> apiProvider) {
    return new SearchVideoPresenter_Factory(apiProvider);
  }
}
