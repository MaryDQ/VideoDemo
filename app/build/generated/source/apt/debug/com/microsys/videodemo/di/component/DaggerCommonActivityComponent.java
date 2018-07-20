package com.microsys.videodemo.di.component;

import com.microsys.videodemo.HomeActivity;
import com.microsys.videodemo.base.BaseActivity_MembersInjector;
import com.microsys.videodemo.base.BaseNoTitleActivity_MembersInjector;
import com.microsys.videodemo.ui.activities.category.CategoryActivity;
import com.microsys.videodemo.ui.activities.location.LocationActivity;
import com.microsys.videodemo.ui.activities.search.SearchActivity;
import com.microsys.videodemo.ui.presenter.GetAllLocationSimpleInfoPresenter;
import com.microsys.videodemo.ui.presenter.GetRecommendListPresenter;
import com.microsys.videodemo.ui.presenter.NewCategoryListPresenter;
import com.microsys.videodemo.ui.presenter.SearchVideoPresenter;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerCommonActivityComponent implements CommonActivityComponent {
  private AppComponent appComponent;

  private DaggerCommonActivityComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  private NewCategoryListPresenter getNewCategoryListPresenter() {
    return new NewCategoryListPresenter(
        Preconditions.checkNotNull(
            appComponent.api(), "Cannot return null from a non-@Nullable component method"));
  }

  private GetRecommendListPresenter getGetRecommendListPresenter() {
    return new GetRecommendListPresenter(
        Preconditions.checkNotNull(
            appComponent.api(), "Cannot return null from a non-@Nullable component method"));
  }

  private SearchVideoPresenter getSearchVideoPresenter() {
    return new SearchVideoPresenter(
        Preconditions.checkNotNull(
            appComponent.api(), "Cannot return null from a non-@Nullable component method"));
  }

  private GetAllLocationSimpleInfoPresenter getGetAllLocationSimpleInfoPresenter() {
    return new GetAllLocationSimpleInfoPresenter(
        Preconditions.checkNotNull(
            appComponent.api(), "Cannot return null from a non-@Nullable component method"));
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.appComponent = builder.appComponent;
  }

  @Override
  public CategoryActivity inject(CategoryActivity activity) {
    return injectCategoryActivity(activity);
  }

  @Override
  public HomeActivity inject(HomeActivity activity) {
    return injectHomeActivity(activity);
  }

  @Override
  public SearchActivity inject(SearchActivity activity) {
    return injectSearchActivity(activity);
  }

  @Override
  public LocationActivity inject(LocationActivity activity) {
    return injectLocationActivity(activity);
  }

  private CategoryActivity injectCategoryActivity(CategoryActivity instance) {
    BaseActivity_MembersInjector.injectPresenter(instance, getNewCategoryListPresenter());
    return instance;
  }

  private HomeActivity injectHomeActivity(HomeActivity instance) {
    BaseNoTitleActivity_MembersInjector.injectPresenter(instance, getGetRecommendListPresenter());
    return instance;
  }

  private SearchActivity injectSearchActivity(SearchActivity instance) {
    BaseNoTitleActivity_MembersInjector.injectPresenter(instance, getSearchVideoPresenter());
    return instance;
  }

  private LocationActivity injectLocationActivity(LocationActivity instance) {
    BaseActivity_MembersInjector.injectPresenter(instance, getGetAllLocationSimpleInfoPresenter());
    return instance;
  }

  public static final class Builder {
    private AppComponent appComponent;

    private Builder() {}

    public CommonActivityComponent build() {
      if (appComponent == null) {
        throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
      }
      return new DaggerCommonActivityComponent(this);
    }

    public Builder appComponent(AppComponent appComponent) {
      this.appComponent = Preconditions.checkNotNull(appComponent);
      return this;
    }
  }
}
