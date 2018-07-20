package com.microsys.videodemo.di.module;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_GetApplicationFactory implements Factory<Application> {
  private final AppModule module;

  public AppModule_GetApplicationFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public Application get() {
    return Preconditions.checkNotNull(
        module.getApplication(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static AppModule_GetApplicationFactory create(AppModule module) {
    return new AppModule_GetApplicationFactory(module);
  }

  public static Application proxyGetApplication(AppModule instance) {
    return Preconditions.checkNotNull(
        instance.getApplication(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
