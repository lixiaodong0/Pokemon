package com.lixd.pokemon.util

import java.security.MessageDigest

object Md5Util {
    fun md5(str: String): String {
        //创建 MessageDigest 实例
        val digest = MessageDigest.getInstance("MD5")
        // 将字符串转换为字节数组
        val bytes = str.toByteArray(Charsets.UTF_8)
        // 计算哈希值
        val hashBytes = digest.digest(bytes)
        // 将字节数组转换为十六进制字符串
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}

fun String.md5(): String {
    return Md5Util.md5(this)
}