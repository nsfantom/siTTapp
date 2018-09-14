package tm.fantom.siapp.ui.list

import tm.fantom.siapp.model.SiContact
import tm.fantom.siapp.ui.base.BaseContract


class ListContract {

    interface View: BaseContract.View {
        fun loadDataSuccess(list: MutableList<SiContact>)
        fun showCancelSnak()
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun itemClicked(idItem:Int)
        fun loadData(text:String)
        fun orderToDelete(contact: SiContact)
        fun deleteItems()
        fun cancelDelete()
    }
}