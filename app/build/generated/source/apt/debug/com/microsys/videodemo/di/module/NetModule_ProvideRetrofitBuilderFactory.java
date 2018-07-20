package com.microsys.videodemo.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetModule_ProvideRetrofitBuilderFactory implements Factory<Retrofit.Builder> {
  private final NetModule module;

  public NetModule_ProvideRetrofitBuilderFactory(NetModule module) {
    this.module = module;
  }

  @Override
  public Retrofit.Builder get() {
    return Preconditions.checkNotNull(
        module.provideRetrofitBuilder(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static NetModule_ProvideRetrofitBuilderFactory create(NetModule module) {
    return new NetModule_ProvideRetrofitBuilderFactory(module);
  }

  public static Retrofit.Builder proxyProvideRetrofitBuilder(NetModule instance) {
    return Preconditions.checkNotNull(
        instance.provideRetrofitBuilder(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
