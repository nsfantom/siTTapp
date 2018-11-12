package tm.fantom.siapp.ui.list

import android.content.Context
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import tm.fantom.siapp.R
import tm.fantom.siapp.di.component.DaggerFragmentComponent
import tm.fantom.siapp.di.module.FragmentModule
import tm.fantom.siapp.event.CreateEvent
import tm.fantom.siapp.event.LoadDataEvent
import tm.fantom.siapp.model.SiContact
import tm.fantom.siapp.utils.SwipeToDelete
import javax.inject.Inject


class ListFragment : Fragment(), ListContract.View, ListAdapter.onItemClickListener {

    @Inject
    lateinit var presenter: ListContract.Presenter
    lateinit var adapter: ListAdapter

    override fun itemDetail(idItem: Int) {
        presenter.itemClicked(idItem)
    }

    override fun itemRemoved(contact: SiContact) {
        presenter.orderToDelete(contact)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        initView()
    }

    private fun injectDependency() {
        val listComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule())
                .build()
        listComponent.inject(this)
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        presenter.loadData(getString(R.string.app_name))
    }

    private fun initView() {
        adapter = ListAdapter(activity!!, this)
        rvItems.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rvItems.adapter = adapter
        val divider = DividerItemDecoration(context, LinearLayout.VERTICAL)
        divider.setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.divider, null)!!)
        rvItems.addItemDecoration(divider)

        val swipeHandler = object : SwipeToDelete(activity!!) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterL = rvItems.adapter as ListAdapter
                adapterL.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rvItems)
        fabButton.setOnClickListener {
            EventBus.getDefault().post(CreateEvent())
        }
    }

    override fun loadDataSuccess(list: MutableList<SiContact>) {
        adapter.updateData(list)
    }

    override fun showCancelSnak() {
        val sCancel = Snackbar.make(rvItems, getString(R.string.contact_del), Snackbar.LENGTH_SHORT)
        sCancel.setAction(getString(R.string.cancel)) { presenter.cancelDelete() }
        SnackHelper(sCancel, SnackHelper.OnDissmissListener { presenter.deleteItems() })
        sCancel.show()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: LoadDataEvent) {
        loadDataSuccess(event.itemList)
    }


    companion object {
        internal fun newInstance(): ListFragment {
            return ListFragment()
        }
    }
}
