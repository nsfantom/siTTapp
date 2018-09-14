package tm.fantom.siapp.repo

import tm.fantom.siapp.R
import tm.fantom.siapp.model.SiContact

class Repository {
    var itemList: ArrayList<SiContact> = ArrayList()

    val imgs = arrayListOf(
            R.drawable.constructionandroid,
            R.drawable.emoandroid,
            R.drawable.labandroid,
            R.drawable.pirateandroid,
            R.drawable.frankendroid
    )

    fun initDummy(){
        imgs.forEachIndexed { index, _ -> itemList.add(SiContact(itemList.size, "firstDummy".plus(index), index.toString().plus("lastdummy"), imgs.get(index))) }
    }

}