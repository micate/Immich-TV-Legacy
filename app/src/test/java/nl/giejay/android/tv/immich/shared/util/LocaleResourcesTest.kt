package nl.giejay.android.tv.immich.shared.util

import android.app.Application
import android.content.res.Configuration
import nl.giejay.android.tv.immich.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23], application = Application::class)
class LocaleResourcesTest {
    private fun resources(languageTag: String) = RuntimeEnvironment.getApplication().let { app ->
        val config = Configuration(app.resources.configuration)
        config.setLocale(Locale.forLanguageTag(languageTag))
        app.createConfigurationContext(config).resources
    }

    @Test
    fun `Chinese system locale selects app and player translations on Android 6`() {
        val res = resources("zh-CN")
        assertEquals("时间线", res.getString(R.string.timeline))
        assertEquals("使用 API 密钥登录", res.getString(R.string.auth_sign_in_by_api_key))
        assertEquals("播放", res.getString(com.zeuskartik.mediaslider.R.string.media_play))
        assertEquals("1 年前", res.getQuantityString(R.plurals.years_ago, 1, 1))
        assertEquals("2 年前", res.getQuantityString(R.plurals.years_ago, 2, 2))
        assertEquals("或在 example.org 输入验证码 1234", res.getString(R.string.or_enter_code_on_auth_url, "1234", "example.org"))
    }

    @Test
    fun `English and untranslated languages fall back without forcing Chinese`() {
        for (tag in listOf("en-US", "ja-JP")) {
            val res = resources(tag)
            assertEquals("Timeline", res.getString(R.string.timeline))
            assertEquals("Play", res.getString(com.zeuskartik.mediaslider.R.string.media_play))
        }
        assertEquals("Immich TV Legacy", resources("zh-CN").getString(R.string.app_name))
    }
}
