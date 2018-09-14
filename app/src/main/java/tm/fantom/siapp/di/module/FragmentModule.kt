package tm.fantom.siapp.di.module


import dagger.Module
import dagger.Provides
import tm.fantom.siapp.ui.edit.EditContract
import tm.fantom.siapp.ui.edit.EditPresenter
import tm.fantom.siapp.ui.list.ListContract
import tm.fantom.siapp.ui.list.ListPresenter

@Module
class FragmentModule {

    @Provides
    fun provideEditPresenter(): EditContract.Presenter {
        return EditPresenter()
    }

    @Provides
    fun provideListPresenter(): ListContract.Presenter {
        return ListPresenter()
    }

}