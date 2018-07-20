package com.microsys.videodemo.ui.activities.category;

import com.microsys.videodemo.base.BaseActivity_MembersInjector;
import com.microsys.videodemo.ui.presenter.NewCategoryListPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CategoryActivity_MembersInjector implements MembersInjector<CategoryActivity> {
  private final Provider<NewCategoryListPresenter> presenterProvider;

  public CategoryActivity_MembersInjector(Provider<NewCategoryListPresenter> presenterProvider) {
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<CategoryActivity> create(
      Provider<NewCategoryListPresenter> presenterProvider) {
    return new CategoryActivity_MembersInjector(presenterProvider);
  }

  @Override
  public void injectMembers(CategoryActivity instance) {
    BaseActivity_MembersInjector.injectPresenter(instance, presenterProvider.get());
  }
}
