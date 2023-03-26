package com.example.tarkovloot.app.di

import com.example.tarkovloot.core_network.main.MainSource
import com.example.tarkovloot.core_network.main.RetrofitMainSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {

    @Binds
    abstract fun bindMainSources(
        retrofitMainSource: RetrofitMainSource
    ): MainSource
}