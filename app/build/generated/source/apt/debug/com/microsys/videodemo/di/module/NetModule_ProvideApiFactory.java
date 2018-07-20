package com.microsys.videodemo.di.module;

import com.google.gson.Gson;
import com.microsys.videodemo.api.Api;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetModule_ProvideApiFactory implements Factory<Api> {
  private final NetModule module;

  private final Provider<Retrofit> retrofitProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<OkHttpClient> okHttpClientProvider;

  public NetModule_ProvideApiFactory(
      NetModule module,
      Provider<Retrofit> retrofitProvider,
      Provider<Gson> gsonProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    this.module = module;
    this.retrofitProvider = retrofitProvider;
    this.gsonProvider = gsonProvider;
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public Api get() {
    return Preconditions.checkNotNull(
        module.provideApi(retrofitProvider.get(), gsonProvider.get(), okHttpClientProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static NetModule_ProvideApiFactory create(
      NetModule module,
      Provider<Retrofit> retrofitProvider,
      Provider<Gson> gsonProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    return new NetModule_ProvideApiFactory(
        module, retrofitProvider, gsonProvider, okHttpClientProvider);
  }

  public static Api proxyProvideApi(
      NetModule instance, Retrofit retrofit, Gson gson, OkHttpClient okHttpClient) {
    return Preconditions.checkNotNull(
        instance.provideApi(retrofit, gson, okHttpClient),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
