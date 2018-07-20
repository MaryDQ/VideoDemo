package com.microsys.videodemo.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final NetModule module;

  private final Provider<OkHttpClient.Builder> builderProvider;

  public NetModule_ProvideOkHttpClientFactory(
      NetModule module, Provider<OkHttpClient.Builder> builderProvider) {
    this.module = module;
    this.builderProvider = builderProvider;
  }

  @Override
  public OkHttpClient get() {
    return Preconditions.checkNotNull(
        module.provideOkHttpClient(builderProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static NetModule_ProvideOkHttpClientFactory create(
      NetModule module, Provider<OkHttpClient.Builder> builderProvider) {
    return new NetModule_ProvideOkHttpClientFactory(module, builderProvider);
  }

  public static OkHttpClient proxyProvideOkHttpClient(
      NetModule instance, OkHttpClient.Builder builder) {
    return Preconditions.checkNotNull(
        instance.provideOkHttpClient(builder),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
