package io.github.iml1s.tx.utils

object Hex {
    fun decode(hex: String): ByteArray {
        val clean = if (hex.length % 2 != 0) "0$hex" else hex
        return clean.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
    }
    
    fun encode(bytes: ByteArray): String {
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
