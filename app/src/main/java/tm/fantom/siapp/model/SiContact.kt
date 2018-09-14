package tm.fantom.siapp.model

import android.support.annotation.DrawableRes

/**
 * Class which provides a model for contact
 * @constructor Sets all properties of the contact
 * @property firstName the last name of the contact
 * @property lastName the last name of the contact
 * @property image the avatar of the contact
 * @property id the current index in repo
 */
data class SiContact(var id: Int, var firstName: String, var lastName: String, @DrawableRes var image: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SiContact

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}