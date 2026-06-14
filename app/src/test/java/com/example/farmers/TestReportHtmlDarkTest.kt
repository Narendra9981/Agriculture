package com.example.farmers

import org.junit.Assert.*
import org.junit.Test

class TestReportHtmlDarkTest {
    @Test
    fun `dark html summary exists and contains headings`() {
        val classLoader = javaClass.classLoader ?: ClassLoader.getSystemClassLoader()
        val resource = classLoader.getResource("test_report_summary_dark.html")
        assertNotNull("Dark HTML summary resource should exist", resource)

        val content = resource!!.readText()
        assertTrue(content.contains("A2ZEE E2E Test Suite Summary"))
        assertTrue(content.contains("100%"))
        assertTrue(content.contains("Registration"))
    }
}
