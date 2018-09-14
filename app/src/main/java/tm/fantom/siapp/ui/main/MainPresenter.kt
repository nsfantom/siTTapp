package tm.fantom.siapp.ui.main

import org.greenrobot.eventbus.EventBus
import tm.fantom.siapp.event.ItemEditEvent
import tm.fantom.siapp.event.LoadDataEvent
import tm.fantom.siapp.event.LoadImagesEvent
import tm.fantom.siapp.model.SiContact
import tm.fantom.siapp.repo.Repository

class MainPresenter : MainContract.Presenter {

    private lateinit var view: MainContract.View

    private lateinit var repository: Repository


    override fun attach(view: MainContract.View) {
        this.view = view
        view.showListFragment() // as default
    }

    override fun initRepo(repository: Repository) {
        this.repository = repository
        repository.initDummy()
    }

    override fun editItem(idItem: Int) {
        view.showAddFragment()
        if (idItem == -1) {
            EventBus.getDefault().postSticky(ItemEditEvent(SiContact(repository.itemList.size, "", "", repository.imgs[0])))
        } else {
            EventBus.getDefault().postSticky(ItemEditEvent(repository.itemList[idItem]))
        }
        EventBus.getDefault().postSticky(LoadImagesEvent(repository.imgs))
    }


    override fun deleteItem(contact: SiContact) {
        repository.itemList.remove(contact)
    }

    override fun updateItem(contact: SiContact) {
        if (repository.itemList.size == contact.id) {
            repository.itemList.add(contact)
        } else {
            repository.itemList[contact.id] = contact
        }
    }


    override fun getAllItems() {
        EventBus.getDefault().post(LoadDataEvent(repository.itemList))
    }

}