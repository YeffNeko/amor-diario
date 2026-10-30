package com.example

import com.example.data.LoveCategory
import com.example.data.LoveMessagesRepository
import com.example.data.LoveProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testAllMessagesNotEmpty() {
        val messages = LoveMessagesRepository.allMessages
        assertTrue(messages.size >= 20)
    }

    @Test
    fun testAllCategoriesRepresented() {
        LoveCategory.values().forEach { category ->
            val count = LoveMessagesRepository.allMessages.count { it.category == category }
            assertTrue("Category $category should have messages", count > 0)
        }
    }

    @Test
    fun testLoveCouponsAndReasons() {
        assertTrue(LoveMessagesRepository.loveCoupons.isNotEmpty())
        assertTrue(LoveMessagesRepository.loveReasons.isNotEmpty())
    }

    @Test
    fun testMessageFormatting() {
        val raw = "Hola {novia}, eres mi {apodo}. Con amor, {novio}."
        val profile = LoveProfile(
            girlfriendName = "Valentina",
            girlfriendNickname = "Mi Princesa",
            boyfriendName = "Carlos",
            anniversaryTimestamp = 0L
        )

        val formatted = raw
            .replace("{novia}", profile.girlfriendName)
            .replace("{apodo}", profile.girlfriendNickname)
            .replace("{novio}", profile.boyfriendName)

        assertEquals("Hola Valentina, eres mi Mi Princesa. Con amor, Carlos.", formatted)
    }

    @Test
    fun testDailyMessageSelection() {
        val msg = LoveMessagesRepository.getDailyMessage(150)
        assertNotNull(msg)
        assertNotNull(msg.title)
        assertNotNull(msg.text)
    }

    @Test
    fun testCoupleQuestions() {
        val questions = com.example.data.CoupleQuestionsRepository.allQuestions
        assertTrue(questions.size >= 15)

        val dailyQ = com.example.data.CoupleQuestionsRepository.getDailyQuestion(100)
        assertNotNull(dailyQ)
        assertTrue(dailyQ.question.isNotBlank())

        val categories = com.example.data.CoupleQuestionsRepository.categories
        assertTrue(categories.contains("Románticas"))
        assertTrue(categories.contains("Profundas"))
    }
}
