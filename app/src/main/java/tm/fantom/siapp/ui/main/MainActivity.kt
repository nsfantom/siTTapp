package tm.fantom.siapp.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import tm.fantom.siapp.R
import tm.fantom.siapp.di.component.DaggerActivityComponent
import tm.fantom.siapp.di.module.ActivityModule
import tm.fantom.siapp.event.*
import tm.fantom.siapp.repo.Repository
import tm.fantom.siapp.ui.edit.EditFragment
import tm.fantom.siapp.ui.list.ListFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter
    @Inject
    lateinit var repository: Repository

    override fun showAddFragment() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(android.R.id.content, EditFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }

    override fun showListFragment() {
        supportFragmentManager.beginTransaction()
                .add(android.R.id.content, ListFragment.newInstance())
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        EventBus.getDefault().register(this)
        presenter.initRepo(repository)
        presenter.attach(this)

    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: ItemUpdateEvent) {
        presenter.updateItem(event.contact)
        onBackPressed()
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: ItemGetEvent) {
        presenter.editItem(event.idItem)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: ItemDeleteEvent) {
        presenter.deleteItem(event.contact)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: GetItemsEvent) {
        presenter.getAllItems()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: CreateEvent) {
        presenter.editItem(-1)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: ShowHomeActionBar) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(event.isShow)
        supportActionBar!!.title = event.text
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
