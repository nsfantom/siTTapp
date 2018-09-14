package tm.fantom.siapp.di.component

import android.content.res.Resources
import dagger.Component
import tm.fantom.siapp.App
import tm.fantom.siapp.di.module.AppModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun resources(): Resources

    fun inject(application: App)
}