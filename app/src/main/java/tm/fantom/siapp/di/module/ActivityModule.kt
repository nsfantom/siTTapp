package tm.fantom.siapp.di.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import tm.fantom.siapp.repo.Repository
import tm.fantom.siapp.ui.main.MainContract
import tm.fantom.siapp.ui.main.MainPresenter

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun providePresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    fun provideRepository(): Repository{
        return Repository()
    }
}