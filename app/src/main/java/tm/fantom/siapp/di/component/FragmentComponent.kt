package tm.fantom.siapp.di.component


import android.content.res.Resources
import dagger.Component
import tm.fantom.siapp.di.module.AppModule
import tm.fantom.siapp.di.module.FragmentModule
import tm.fantom.siapp.ui.edit.EditFragment
import tm.fantom.siapp.ui.list.ListFragment


@Component(modules = arrayOf(FragmentModule::class, AppModule::class))
interface FragmentComponent {

    fun inject(editFragment: EditFragment)

    fun inject(listFragment: ListFragment)

}