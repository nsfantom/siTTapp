package tm.fantom.siapp.ui.edit

import org.greenrobot.eventbus.EventBus
import tm.fantom.siapp.event.ItemUpdateEvent
import tm.fantom.siapp.model.SiContact


class EditPresenter : EditContract.Presenter {
    private lateinit var view: EditContract.View
    private lateinit var imgs: ArrayList<Int>
    private lateinit var contact: SiContact

    override fun attach(view: EditContract.View) {
        this.view = view
    }

    override fun onLoadContact(contact: SiContact) {
        this.contact = contact
        view.loadContactSuccess(contact)
        view.updateTitle(contact.firstName, contact.lastName)
    }

    override fun onLoadedImages(imgs: ArrayList<Int>) {
        this.imgs = imgs
        view.loadImagesSuccess(imgs)
    }


    override fun saveContact() {
        view.errorLN(contact.lastName.isEmpty())
        view.errorFN(contact.firstName.isEmpty())
        if (!contact.firstName.isEmpty() && !contact.lastName.isEmpty()) {
            EventBus.getDefault().post(ItemUpdateEvent(contact))
        }
    }

    override fun onFirstNameChanged(txt: String) {
        view.errorFN(txt.isEmpty())
        contact.firstName = txt
        view.updateTitle(txt, contact.lastName)
    }

    override fun onLastNameChanged(txt: String) {
        view.errorLN(txt.isEmpty())
        contact.lastName = txt
        view.updateTitle(contact.firstName, txt)
    }


    override fun updateImagesSelection(image: Int) {
        contact.image = image
        view.loadImagesSuccess(imgs)
    }
}