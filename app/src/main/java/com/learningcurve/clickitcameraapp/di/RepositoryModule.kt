package com.learningcurve.clickitcameraapp.di

import com.learningcurve.clickitcameraapp.presentation.repository.EditImageRepository
import com.learningcurve.clickitcameraapp.presentation.repository.EditImageRepositoryImpl
import com.learningcurve.clickitcameraapp.util.FileHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideEditImageRepository(
        fileHelper: FileHelper,
    ): EditImageRepository {
        return EditImageRepositoryImpl( fileHelper)
    }

}