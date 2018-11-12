package tm.fantom.siapp.ui.edit

import androidx.annotation.DrawableRes
import tm.fantom.siapp.model.SiContact
import tm.fantom.siapp.ui.base.BaseContract

class EditContract {

    interface View : BaseContract.View {
        fun loadImagesSuccess(imgs:ArrayList<Int>)
        fun loadContactSuccess(contact: SiContact)
        fun errorFN(isError: Boolean)
        fun errorLN(isError: Boolean)
        fun updateTitle(fn: String, ln: String)

    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onLoadedImages(imgs:ArrayList<Int>)
        fun onLoadContact(contact: SiContact)
        fun saveContact()
        fun updateImagesSelection(@DrawableRes image: Int)
        fun onFirstNameChanged(txt:String)
        fun onLastNameChanged(txt:String)
    }
}