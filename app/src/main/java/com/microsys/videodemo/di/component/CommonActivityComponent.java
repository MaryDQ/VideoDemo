package com.microsys.videodemo.di.component;


import com.microsys.videodemo.HomeActivity;
import com.microsys.videodemo.di.ActivityScope;
import com.microsys.videodemo.ui.activities.category.CategoryActivity;
import com.microsys.videodemo.ui.activities.location.LocationActivity;
import com.microsys.videodemo.ui.activities.search.SearchActivity;

import dagger.Component;

/**
 * ============================
 * 作    者：mlx
 * 创建日期：2018/1/16.
 * 描    述：通用Activity
 * 修改历史：
 * ===========================
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface CommonActivityComponent {
    CategoryActivity inject(CategoryActivity activity);

    HomeActivity inject(HomeActivity activity);

    SearchActivity inject(SearchActivity activity);

    LocationActivity inject(LocationActivity activity);
}
