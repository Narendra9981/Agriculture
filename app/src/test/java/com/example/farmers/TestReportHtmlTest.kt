package com.example.farmers

import org.junit.Assert.*
import org.junit.Test

class TestReportHtmlTest {
    @Test
    fun `report html present and has summary header`() {
        val classLoader = javaClass.classLoader ?: ClassLoader.getSystemClassLoader()
        val resource = classLoader.getResource("test_report_summary.html")
        assertNotNull("HTML summary resource should exist", resource)

        val content = resource!!.readText()
        assertTrue(content.contains("A2ZEE E2E Test Suite Summary"))
        assertTrue(content.contains("100% successful"))
        assertTrue(content.contains("Registration"))
    }
}
