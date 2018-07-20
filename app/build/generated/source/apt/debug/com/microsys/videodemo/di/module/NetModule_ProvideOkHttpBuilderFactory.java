package com.microsys.videodemo.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import okhttp3.OkHttpClient;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetModule_ProvideOkHttpBuilderFactory implements Factory<OkHttpClient.Builder> {
  private final NetModule module;

  public NetModule_ProvideOkHttpBuilderFactory(NetModule module) {
    this.module = module;
  }

  @Override
  public OkHttpClient.Builder get() {
    return Preconditions.checkNotNull(
        module.provideOkHttpBuilder(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static NetModule_ProvideOkHttpBuilderFactory create(NetModule module) {
    return new NetModule_ProvideOkHttpBuilderFactory(module);
  }

  public static OkHttpClient.Builder proxyProvideOkHttpBuilder(NetModule instance) {
    return Preconditions.checkNotNull(
        instance.provideOkHttpBuilder(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
