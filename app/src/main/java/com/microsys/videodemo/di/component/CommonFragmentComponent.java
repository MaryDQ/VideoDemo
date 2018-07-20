package com.microsys.videodemo.di.component;


import com.microsys.videodemo.di.FragmentScope;
import com.microsys.videodemo.ui.fragments.VideoFragment;

import dagger.Component;

/**
 * ============================
 * 作    者：mlx
 * 创建日期：2018/1/22.
 * 描    述：通用fragment
 * 修改历史：
 * ===========================
 */

@FragmentScope
@Component(dependencies = AppComponent.class)
public interface CommonFragmentComponent {
    //    TestFragment inject(TestFragment fragment);
    VideoFragment inject(VideoFragment fragment);
}
