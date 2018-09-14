package tm.fantom.siapp.ui.list

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_contact.view.*
import tm.fantom.siapp.R
import tm.fantom.siapp.model.SiContact

class ListAdapter(private val context: Context, fragment: Fragment) : RecyclerView.Adapter<ListAdapter.ContactViewHolder>() {

    private val listener: onItemClickListener
    private val list: MutableList<SiContact> = ArrayList()

    init {
        this.listener = fragment as onItemClickListener
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        var contact = list[position]

        holder.itemView.tvFullName.text = contact.firstName.plus(" ").plus(contact.lastName)
        holder.itemView.avatar.setImageResource(contact.image)

        holder.itemView.llRow.setOnClickListener { listener.itemDetail(contact.id) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_contact, parent, false)
        return ContactViewHolder(itemView.root)
    }

    fun updateData(list: MutableList<SiContact>){
        this.list.clear()
        this.list.addAll(list.sortedWith(compareBy(SiContact::firstName, SiContact::lastName)))
        notifyDataSetChanged()
    }

    fun getItemByPosition(position: Int): SiContact{
        return list[position]
    }

    fun removeAt(position: Int) {
        listener.itemRemoved(list[position])
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface onItemClickListener {
        fun itemDetail(idItem: Int)
        fun itemRemoved(contact: SiContact)
    }
}