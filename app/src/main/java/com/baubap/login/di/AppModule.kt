package com.baubap.login.di

import com.baubap.login.data.repository.LoginRepositoryImpl
import com.baubap.login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBinds {

  @Binds
  abstract fun bindLoginRepositoryImpl(impl: LoginRepositoryImpl): LoginRepository
}