package com.microsys.videodemo.di.component;

import android.app.Application;
import com.google.gson.Gson;
import com.microsys.videodemo.api.Api;
import com.microsys.videodemo.di.module.AppModule;
import com.microsys.videodemo.di.module.AppModule_GetApplicationFactory;
import com.microsys.videodemo.di.module.NetModule;
import com.microsys.videodemo.di.module.NetModule_ProvideApiFactory;
import com.microsys.videodemo.di.module.NetModule_ProvideGsonFactory;
import com.microsys.videodemo.di.module.NetModule_ProvideOkHttpBuilderFactory;
import com.microsys.videodemo.di.module.NetModule_ProvideOkHttpClientFactory;
import com.microsys.videodemo.di.module.NetModule_ProvideRetrofitBuilderFactory;
import com.microsys.videodemo.di.module.NetModule_ProvideRetrofitFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerAppComponent implements AppComponent {
  private Provider<Application> getApplicationProvider;

  private Provider<Retrofit.Builder> provideRetrofitBuilderProvider;

  private Provider<OkHttpClient.Builder> provideOkHttpBuilderProvider;

  private Provider<OkHttpClient> provideOkHttpClientProvider;

  private Provider<Gson> provideGsonProvider;

  private Provider<Retrofit> provideRetrofitProvider;

  private Provider<Api> provideApiProvider;

  private DaggerAppComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.getApplicationProvider =
        DoubleCheck.provider(AppModule_GetApplicationFactory.create(builder.appModule));
    this.provideRetrofitBuilderProvider =
        DoubleCheck.provider(NetModule_ProvideRetrofitBuilderFactory.create(builder.netModule));
    this.provideOkHttpBuilderProvider =
        DoubleCheck.provider(NetModule_ProvideOkHttpBuilderFactory.create(builder.netModule));
    this.provideOkHttpClientProvider =
        DoubleCheck.provider(
            NetModule_ProvideOkHttpClientFactory.create(
                builder.netModule, provideOkHttpBuilderProvider));
    this.provideGsonProvider =
        DoubleCheck.provider(NetModule_ProvideGsonFactory.create(builder.netModule));
    this.provideRetrofitProvider =
        DoubleCheck.provider(
            NetModule_ProvideRetrofitFactory.create(
                builder.netModule,
                provideRetrofitBuilderProvider,
                provideOkHttpClientProvider,
                provideGsonProvider));
    this.provideApiProvider =
        DoubleCheck.provider(
            NetModule_ProvideApiFactory.create(
                builder.netModule,
                provideRetrofitProvider,
                provideGsonProvider,
                provideOkHttpClientProvider));
  }

  @Override
  public Application application() {
    return getApplicationProvider.get();
  }

  @Override
  public Api api() {
    return provideApiProvider.get();
  }

  public static final class Builder {
    private AppModule appModule;

    private NetModule netModule;

    private Builder() {}

    public AppComponent build() {
      if (appModule == null) {
        throw new IllegalStateException(AppModule.class.getCanonicalName() + " must be set");
      }
      if (netModule == null) {
        this.netModule = new NetModule();
      }
      return new DaggerAppComponent(this);
    }

    public Builder appModule(AppModule appModule) {
      this.appModule = Preconditions.checkNotNull(appModule);
      return this;
    }

    public Builder netModule(NetModule netModule) {
      this.netModule = Preconditions.checkNotNull(netModule);
      return this;
    }
  }
}
