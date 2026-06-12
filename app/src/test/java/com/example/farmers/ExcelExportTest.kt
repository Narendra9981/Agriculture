package com.example.farmers

import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for the disease report CSV export sample file.
 * This test confirms the sample resource exists and contains the expected
 * report fields used for Excel/CSV export validation.
 */
class ExcelExportTest {
    private val resourceName = "disease_report_sample.csv"

    @Test
    fun `verify sample disease report CSV is present and contains header fields`() {
        val classLoader = javaClass.classLoader ?: ClassLoader.getSystemClassLoader()
        val resource = classLoader.getResource(resourceName)
        assertNotNull("Sample CSV resource should be available in test resources", resource)

        val content = resource!!.readText()
        assertTrue(content.contains("Field,Value"))
        assertTrue(content.contains("Disease,Early Blight"))
    }

    @Test
    fun `verify sample disease report CSV contains all expected rows`() {
        val classLoader = javaClass.classLoader ?: ClassLoader.getSystemClassLoader()
        val resource = classLoader.getResource(resourceName)
        assertNotNull(resource)

        val rows = resource!!.readText().lines().filter { it.isNotBlank() }
        assertEquals(11, rows.size)
        assertEquals("Crop,Tomato", rows[1])
        assertEquals("Health Score,42%", rows[5])
        assertEquals("Prevention,Boost air circulation; use drip irrigation; use resistant seeds", rows[10])
    }
}
