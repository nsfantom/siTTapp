package tm.fantom.siapp.di.component

import dagger.Component
import tm.fantom.siapp.ui.main.MainActivity
import tm.fantom.siapp.di.module.ActivityModule

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

}