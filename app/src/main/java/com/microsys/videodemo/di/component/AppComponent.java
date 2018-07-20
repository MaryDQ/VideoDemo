package com.microsys.videodemo.di.component;

import android.app.Application;

import com.microsys.videodemo.api.Api;
import com.microsys.videodemo.di.module.AppModule;
import com.microsys.videodemo.di.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ============================
 * 作    者：mlx
 * 创建日期：2017/12/28.
 * 描    述：
 * 修改历史：
 * ===========================
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    Application application();

//    IRepositoryManager repositoryManager();

    Api api();
}
