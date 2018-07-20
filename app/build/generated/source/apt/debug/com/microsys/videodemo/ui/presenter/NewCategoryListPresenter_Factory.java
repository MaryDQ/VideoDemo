package com.microsys.videodemo.ui.presenter;

import com.microsys.videodemo.api.Api;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NewCategoryListPresenter_Factory implements Factory<NewCategoryListPresenter> {
  private final Provider<Api> apiProvider;

  public NewCategoryListPresenter_Factory(Provider<Api> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public NewCategoryListPresenter get() {
    return new NewCategoryListPresenter(apiProvider.get());
  }

  public static NewCategoryListPresenter_Factory create(Provider<Api> apiProvider) {
    return new NewCategoryListPresenter_Factory(apiProvider);
  }
}
