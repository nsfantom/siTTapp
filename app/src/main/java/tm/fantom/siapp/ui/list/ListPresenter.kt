package tm.fantom.siapp.ui.list


import org.greenrobot.eventbus.EventBus
import tm.fantom.siapp.event.*
import tm.fantom.siapp.model.SiContact


class ListPresenter : ListContract.Presenter {


    private lateinit var view: ListContract.View
    private val itemsToDel = ArrayList<SiContact>()

    override fun attach(view: ListContract.View) {
        this.view = view
    }

    override fun loadData(text: String) {
        EventBus.getDefault().post(GetItemsEvent())
        EventBus.getDefault().post(ShowHomeActionBar(false, text))
    }

//    private fun createDetailsViewModel(contact: SiContact): DetailsViewModel {
//
//        return DetailsViewModel(postList, userList, albumList)
//    }

    override fun itemClicked(idItem: Int) {
        EventBus.getDefault().post(ItemGetEvent(idItem))
    }

    override fun orderToDelete(contact: SiContact) {
        itemsToDel.add(contact)
        view.showCancelSnak()
    }

    override fun deleteItems() {
        itemsToDel.forEach { EventBus.getDefault().post(ItemDeleteEvent(it)) }
    }

    override fun cancelDelete() {
        itemsToDel.clear()
        EventBus.getDefault().post(GetItemsEvent())
    }

}