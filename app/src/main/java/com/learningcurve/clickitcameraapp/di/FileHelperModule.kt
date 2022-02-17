package com.learningcurve.clickitcameraapp.di

import android.content.Context
import com.learningcurve.clickitcameraapp.util.FileHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FileHelperModule {

    @Provides
    fun provideFileHelper(
        @ApplicationContext context: Context
    ): FileHelper{
        return FileHelper(context)
    }
}