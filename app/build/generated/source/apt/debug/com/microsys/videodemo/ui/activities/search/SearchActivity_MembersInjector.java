package com.microsys.videodemo.ui.activities.search;

import com.microsys.videodemo.base.BaseNoTitleActivity_MembersInjector;
import com.microsys.videodemo.ui.presenter.SearchVideoPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchActivity_MembersInjector implements MembersInjector<SearchActivity> {
  private final Provider<SearchVideoPresenter> presenterProvider;

  public SearchActivity_MembersInjector(Provider<SearchVideoPresenter> presenterProvider) {
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<SearchActivity> create(
      Provider<SearchVideoPresenter> presenterProvider) {
    return new SearchActivity_MembersInjector(presenterProvider);
  }

  @Override
  public void injectMembers(SearchActivity instance) {
    BaseNoTitleActivity_MembersInjector.injectPresenter(instance, presenterProvider.get());
  }
}
