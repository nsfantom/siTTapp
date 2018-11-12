package tm.fantom.siapp.ui.edit

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatImageView
import android.view.*
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_edit.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import tm.fantom.siapp.R
import tm.fantom.siapp.di.component.DaggerFragmentComponent
import tm.fantom.siapp.di.module.FragmentModule
import tm.fantom.siapp.event.ItemEditEvent
import tm.fantom.siapp.event.LoadImagesEvent
import tm.fantom.siapp.event.ShowHomeActionBar
import tm.fantom.siapp.model.SiContact
import tm.fantom.siapp.utils.DisplayUtil
import javax.inject.Inject


class EditFragment : Fragment(), EditContract.View {

    lateinit var contact: SiContact

    @Inject
    lateinit var presenter: EditContract.Presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        injectDependency()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        etFirstName.addTextChangedListener(SimpleTextWatcher { presenter.onFirstNameChanged(it.toString()) })
        etLastName.addTextChangedListener(SimpleTextWatcher { presenter.onLastNameChanged(it.toString()) })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        val item = menu!!.add(R.string.save).setOnMenuItemClickListener { _ ->
            presenter.saveContact()
            true
        }
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM or MenuItem.SHOW_AS_ACTION_WITH_TEXT)
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: ItemEditEvent) {
        presenter.onLoadContact(event.contact.copy())
        EventBus.getDefault().removeStickyEvent(event)

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: LoadImagesEvent) {
        presenter.onLoadedImages(event.imgs)
        EventBus.getDefault().removeStickyEvent(event)
    }

    override fun loadImagesSuccess(imgs: ArrayList<Int>) {
        llAvatars.removeAllViews()
        imgs.forEachIndexed { index, _ ->
            val img = AppCompatImageView(context)
            img.setImageResource(imgs[index])
            if (contact.image != imgs[index]) {
                img.alpha = 0.2f
            }
            img.layoutParams = LinearLayout.LayoutParams(
                    DisplayUtil.dpToPx(resources, 40),
                    DisplayUtil.dpToPx(resources, 40)
            )
            img.setOnClickListener {
                contact.image = imgs[index]
                presenter.updateImagesSelection(imgs[index])
            }
            llAvatars.addView(img)
        }

    }

    override fun loadContactSuccess(contact: SiContact) {
        this.contact = contact
        etFirstName.setText(contact.firstName)
        etLastName.setText(contact.lastName)
    }

    override fun errorFN(isError: Boolean) {
        if (isError) etFirstName.error = getString(R.string.error_first_name)
        else etFirstName.error = null
    }

    override fun errorLN(isError: Boolean) {
        if (isError) etLastName.error = getString(R.string.error_last_name)
        else etLastName.error = null
    }

    override fun updateTitle(fn: String, ln: String) {
        var first = ""
        var last = ""
        if (ln.isEmpty() && fn.isEmpty()) {
            EventBus.getDefault().post(ShowHomeActionBar(true, "New"))
        } else {
            if (!fn.isEmpty()) first = fn[0].toUpperCase().plus(". ")
            if (!ln.isEmpty()) last = ln[0].toUpperCase().plus(". ")
            EventBus.getDefault().post(ShowHomeActionBar(true, first.plus(last)))
        }
    }

    private fun injectDependency() {
        val editComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule())
                .build()
        editComponent.inject(this)
    }

    companion object {
        internal fun newInstance(): EditFragment {
            return EditFragment()
        }
    }

}