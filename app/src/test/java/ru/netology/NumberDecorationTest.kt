package ru.netology

import org.junit.Test

import org.junit.Assert.*

class NumberDecorationTest {

    @Test
    fun testToString_negative() {
        assertTrue(NumberDecoration(-999).toString() == "-999")
    }

    @Test
    fun testToString_lower1000() {
        assertTrue(NumberDecoration(999).toString() == "999")
    }

    @Test
    fun testToString_lower1000Fail() {
        assertFalse(NumberDecoration(999).toString() == "1K")
    }

    @Test
    fun testToString_kilo1k() {
        assertTrue(NumberDecoration(1_001).toString() == "1K")
    }

    @Test
    fun testToString_kilo1kFail() {
        assertFalse(NumberDecoration(1_001).toString() == "1.1K")
    }

    @Test
    fun testToString_kilo5k1() {
        assertTrue(NumberDecoration(5_100).toString() == "5.1K")
    }

    @Test
    fun testToString_negativeKilo5k1() {
        assertTrue(NumberDecoration(-5_100).toString() == "-5.1K")
    }

    @Test
    fun testToString_kilo1k3() {
        assertTrue(NumberDecoration(1_301).toString() == "1.3K")
    }

    @Test
    fun testToString_kilo1k1Fail() {
        assertFalse(NumberDecoration(1_301).toString() == "10K")
    }

    @Test
    fun testToString_kilo10k() {
        assertTrue(NumberDecoration(10_301).toString() == "10K")
    }

    @Test
    fun testToString_10kFail() {
        assertFalse(NumberDecoration(1_301).toString() == "10.3K")
    }

    @Test
    fun testToString_million1m3() {
        assertTrue(NumberDecoration(1_301_000).toString() == "1.3M")
    }

    @Test
    fun testToString_million1m3Fail() {
        assertFalse(NumberDecoration(1_301_000).toString() == "1.1K")
    }



}