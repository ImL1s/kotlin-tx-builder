package io.github.iml1s.tx.bitcoin

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ScriptTest {

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testP2PKH_Generation() {
        // From BIP-32 Test Vector 1, m/0H address: 19Q2WoS5hSS6T8GjhK8KZLMgmWaq4neXrh
        // pubKeyHash: 5c1bd648ed23aa5fd50ba52b2457c11e9e80a6a7
        val pubKeyHash = "5c1bd648ed23aa5fd50ba52b2457c11e9e80a6a7".hexToByteArray()
        
        val script = Script.pay2pkh(pubKeyHash)
        
        // Expected: OP_DUP OP_HASH160 <20 bytes> OP_EQUALVERIFY OP_CHECKSIG
        assertEquals(25, script.size)
        assertTrue(Script.isP2PKH(script))
        assertFalse(Script.isP2WPKH(script))
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testP2WPKH_Generation() {
        val pubKeyHash = "0014".repeat(10).hexToByteArray().sliceArray(0 until 20)
        
        val script = Script.pay2wpkh(pubKeyHash)
        
        // Expected: OP_0 <20 bytes>
        assertEquals(22, script.size)
        assertTrue(Script.isP2WPKH(script))
        assertEquals(0, Script.getWitnessVersion(script))
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testP2WSH_Generation() {
        val scriptHash = ByteArray(32) { it.toByte() }
        
        val script = Script.pay2wsh(scriptHash)
        
        // Expected: OP_0 <32 bytes>
        assertEquals(34, script.size)
        assertTrue(Script.isP2WSH(script))
        assertEquals(0, Script.getWitnessVersion(script))
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testP2TR_Generation() {
        val outputKey = ByteArray(32) { (it + 1).toByte() }
        
        val script = Script.pay2tr(outputKey)
        
        // Expected: OP_1 <32 bytes>
        assertEquals(34, script.size)
        assertTrue(Script.isP2TR(script))
        assertEquals(1, Script.getWitnessVersion(script))
    }

    @Test
    fun testPushData_SmallData() {
        val data = byteArrayOf(0x01, 0x02, 0x03)
        val result = Script.pushData(data)
        
        // Length prefix (3) + data
        assertEquals(4, result.size)
        assertEquals(3, result[0].toInt())
    }

    @Test
    fun testPushData_EmptyData() {
        val result = Script.pushData(byteArrayOf())
        
        // Should be OP_0
        assertEquals(1, result.size)
        assertEquals(OpCodes.OP_0, result[0].toInt() and 0xFF)
    }

    private fun String.hexToByteArray(): ByteArray {
        return chunked(2).map { it.toInt(16).toByte() }.toByteArray()
    }
}
