package com.example.farmers

import org.junit.Assert.*
import org.junit.Test

class TestReportImageTest {
    @Test
    fun `svg image asset exists`() {
        val classLoader = javaClass.classLoader ?: ClassLoader.getSystemClassLoader()
        val resource = classLoader.getResource("test_report_summary_dark.svg")
        assertNotNull("SVG image resource should exist", resource)
        val content = resource!!.readText()
        assertTrue(content.contains("A2ZEE E2E Test Suite Summary"))
    }
}
