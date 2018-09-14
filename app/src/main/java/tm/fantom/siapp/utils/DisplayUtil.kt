package tm.fantom.siapp.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

object DisplayUtil {

    fun dpToPx(resources: Resources, dp: Int): Int {
        // Converts dip into its equivalent px
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics))
    }

    fun dpToPx(context: Context, dp: Int): Int {
        val resources = context.resources
        // Converts dip into its equivalent px
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics))
    }

}
