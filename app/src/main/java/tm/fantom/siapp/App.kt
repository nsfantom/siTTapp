package tm.fantom.siapp

import android.app.Application
import tm.fantom.siapp.di.component.AppComponent
import tm.fantom.siapp.di.component.DaggerAppComponent
import tm.fantom.siapp.di.module.AppModule

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        appComponent.inject(this)
    }

    fun getApplicationComponent(): AppComponent {
        return appComponent
    }

    companion object {
        lateinit var instance: App private set
    }
}