package dev.yllty.fiveidnumforandroid

import okhttp3.OkHttpClient
import okhttp3.Request
import java.security.MessageDigest
import kotlin.random.Random

object NumCompute {

    private val defaultShenRenList = """
        忆兰lty
        yl_lty
        Five_ID_Num
        五字神人检测器
        闪光丸山彩
        潜艇娓娓迷
        账号已注销
        还我神id
        暗杠读秒p
        xiaomulink
        侦探许烦烦
        Maroon Five
        Maroon V
        Maroon 5
        DGLAB
        DG-LAB
        地牢实验室
        vrc局长
        HX2xianglong90
    """.trimIndent()

    private var shenRenList: String? = null

    // 用于初始化shenRenList
    suspend fun initShenRenList() {
        try {
            val url = "https://gitee.com/ylLty/Five_ID_Num/raw/master/shenRenList.txt"
            val text = getHttpContent(url)
            shenRenList = text
            if (shenRenList.isNullOrEmpty()) {
                throw Exception("获取shenRenList文本失败")
            }
        } catch (e: Exception) {
            shenRenList = defaultShenRenList
        }
    }

    // 使用OkHttp获取HTTP内容
    private suspend fun getHttpContent(url: String): String {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("网络请求失败: ${response.code}")
            }
            return response.body?.string() ?: ""
        }
    }

    // 判断ID是否在神人列表中
    fun isIdInShenRenList(id: String): Boolean {
        if (shenRenList.isNullOrEmpty() || id.isEmpty()) return false

        val lines = shenRenList!!.split(Regex("\r\n|\n")).map { it.trim() }
        return lines.any { it.equals(id, ignoreCase = true) }
    }

    // 计算哈希值
    private fun getHash(str: String): Int {
        val md = MessageDigest.getInstance("MD5")
        val bytes = md.digest(str.toByteArray(Charsets.UTF_8))

        // 将MD5哈希字节数组转为一个Int
        return (bytes[0].toInt() shl 24) or
                (bytes[1].toInt() shl 16) or
                (bytes[2].toInt() shl 8) or
                (bytes[3].toInt())
    }

    // 计算结果
    fun getNum(id: String): Int {
        var num = 0
        val hushOfId = Math.abs(getHash(id))

        if (isIdInShenRenList(id)) {
            if (id.length != 5) {
                num = 101
                return num
            }
            num = 102
            return num
        }

        num = hushOfId % 100

        if (id.length == 5) {
            num = when (hushOfId % 10) {
                0 -> 100
                in 1..2 -> 99
                in 3..4 -> 98
                in 5..6 -> 97
                in 7..8 -> 96
                else -> 95
            }
        }

        return num
    }

    // 获取评论
    fun getComment(num: Int, id: String): String {
        var comment = ""
        when {
            id.length == 5 -> comment = chooseComment("正因为是五个字，")
            num >= 70 -> comment = chooseComment("虽然不是5个字,但是", "不是5个字,不过")
            else -> comment = chooseComment("正因为不是5个字,所以", "")
        }

        when (num) {
            in 0..10 -> comment += chooseComment("根本不是神人", "好好当个正常人吧")
            in 11..30 -> comment += chooseComment("你现在处于神人的最早期形态")
            in 31..50 -> comment += chooseComment("离神人还是有点远的", "神人的称号跟你没什么关系")
            in 51..69 -> comment += chooseComment("你处于神人之外，但还没那么远", "还差点意思")
            in 70..90 -> comment += chooseComment("很接近神人了", "离人很远离神，很近了")
            else -> comment += chooseComment("你也是个神人了", "神人有你一席之地")
        }

        return comment
    }

    // 选择评论
    private fun chooseComment(vararg comments: String): String {
        val random = Random.nextInt(comments.size)
        return comments[random]
    }
}