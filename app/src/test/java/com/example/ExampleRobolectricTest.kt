package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Amor Diario", appName)
  }

  @Test
  fun `default_web_client_id is configured`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val webClientId = context.getString(R.string.default_web_client_id)
    assertEquals("276942397016-545v2j2ggr2pecug02lmp36hboltthbs.apps.googleusercontent.com", webClientId)
  }
}
