package nl.giejay.android.tv.immich.shared.util

import java.util.Calendar
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Test

class AssetDateFormattingTest {
    private fun withLocale(locale: Locale, block: () -> Unit) {
        val original = Locale.getDefault()
        try {
            Locale.setDefault(locale)
            block()
        } finally {
            Locale.setDefault(original)
        }
    }

    private fun april(day: Int) = Calendar.getInstance().apply {
        clear()
        set(2006, Calendar.APRIL, day, 12, 0, 0)
    }.time

    @Test
    fun `English dates retain ordinal suffixes`() = withLocale(Locale.US) {
        assertEquals("Saturday, 1st April 2006", formatAssetDate(april(1)))
        assertEquals("Sunday, 2nd April 2006", formatAssetDate(april(2)))
        assertEquals("Monday, 3rd April 2006", formatAssetDate(april(3)))
        assertEquals("Friday, 7th April 2006", formatAssetDate(april(7)))
        assertEquals("Tuesday, 11th April 2006", formatAssetDate(april(11)))
        assertEquals("Friday, 21st April 2006", formatAssetDate(april(21)))
    }

    @Test
    fun `non English dates use the default locale without English ordinals`() = withLocale(Locale.GERMANY) {
        assertEquals("Freitag, 7. April 2006", formatAssetDate(april(7)))
    }
}
