package tm.fantom.siapp.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import tm.fantom.siapp.di.scope.PerApplication
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {

    @Provides
    @Singleton
    @PerApplication
    fun providesApplication() = application

    @Provides
    @Singleton
    fun providesResources() = application.resources

}