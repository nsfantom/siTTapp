package tm.fantom.siapp.ui.main

import tm.fantom.siapp.model.SiContact
import tm.fantom.siapp.repo.Repository
import tm.fantom.siapp.ui.base.BaseContract

class MainContract {

    interface View : BaseContract.View {
//        fun showDetailFragment(idItem:Int)
        fun showListFragment()
        fun showAddFragment()
    }

    interface Presenter: BaseContract.Presenter<MainContract.View> {
        fun initRepo(repository: Repository)
        fun editItem(idItem:Int)
        fun deleteItem(contact: SiContact)
        fun updateItem(contact: SiContact)
        fun getAllItems()
    }
}