package com.example.farmers

import org.junit.Assert.*
import org.junit.Test

class ExcelExportTest {
    @Test
    fun sampleCsvFileExistsAndHasExpectedHeaders() {
        val resource = javaClass.classLoader.getResource("disease_report_sample.csv")
        assertNotNull("Sample CSV resource should be available in test resources", resource)

        val content = resource!!.readText()
        assertTrue(content.contains("Field,Value"))
        assertTrue(content.contains("Disease,Early Blight"))
    }

    @Test
    fun sampleCsvFileContainsExpectedDataRows() {
        val resource = javaClass.classLoader.getResource("disease_report_sample.csv")
        assertNotNull(resource)

        val rows = resource!!.readText().lines().filter { it.isNotBlank() }
        assertEquals(10, rows.size)
        assertEquals("Crop,Tomato", rows[1])
        assertEquals("Health Score,42%", rows[5])
    }
}
