package com.microsys.videodemo.di.module;

import com.google.gson.Gson;
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
public final class NetModule_ProvideRetrofitFactory implements Factory<Retrofit> {
  private final NetModule module;

  private final Provider<Retrofit.Builder> builderProvider;

  private final Provider<OkHttpClient> clientProvider;

  private final Provider<Gson> gsonProvider;

  public NetModule_ProvideRetrofitFactory(
      NetModule module,
      Provider<Retrofit.Builder> builderProvider,
      Provider<OkHttpClient> clientProvider,
      Provider<Gson> gsonProvider) {
    this.module = module;
    this.builderProvider = builderProvider;
    this.clientProvider = clientProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public Retrofit get() {
    return Preconditions.checkNotNull(
        module.provideRetrofit(builderProvider.get(), clientProvider.get(), gsonProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static NetModule_ProvideRetrofitFactory create(
      NetModule module,
      Provider<Retrofit.Builder> builderProvider,
      Provider<OkHttpClient> clientProvider,
      Provider<Gson> gsonProvider) {
    return new NetModule_ProvideRetrofitFactory(
        module, builderProvider, clientProvider, gsonProvider);
  }

  public static Retrofit proxyProvideRetrofit(
      NetModule instance, Retrofit.Builder builder, OkHttpClient client, Gson gson) {
    return Preconditions.checkNotNull(
        instance.provideRetrofit(builder, client, gson),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
