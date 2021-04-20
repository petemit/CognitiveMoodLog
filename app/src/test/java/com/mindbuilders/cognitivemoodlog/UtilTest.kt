package com.mindbuilders.cognitivemoodlog

import com.mindbuilders.cognitivemoodlog.data.toSha1
import org.junit.Assert.assertTrue

import org.junit.Test

class UtilTest {

    @Test
    fun testHashing() {
        val str = "test"
        val one = str.toSha1()
        assertTrue(one == "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3")
    }
}