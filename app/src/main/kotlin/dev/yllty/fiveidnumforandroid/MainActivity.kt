package dev.yllty.fiveidnumforandroid

import android.os.Bundle
import kotlinx.coroutines.*
import android.widget.Toast
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import dev.yllty.fiveidnumforandroid.databinding.ActivityMainBinding
import dev.yllty.fiveidnumforandroid.NumCompute

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = checkNotNull(_binding) { "Activity has been destroyed" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 使用 View Binding 访问视图并设置点击事件
        binding.startButton.setOnClickListener {
            startButtonClick()
        }
        GlobalScope.launch(Dispatchers.Main) {
            // 初始化神人列表
            NumCompute.initShenRenList()
        }
        //dev.yllty.fiveidnumforandroid.NumCompute.initShenRenList()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun startButtonClick() {
        // 触发触觉反馈
        binding.startButton.performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY)
        // 获取输入框内容
        val userInput: String = binding.idEdit.text.toString()
        // 进行其他处理
        if(userInput.isNullOrBlank()){
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show()
            return
        }
        
        val num: Int = NumCompute.getNum(userInput)
        val comment: String = NumCompute.getComment(num, userInput)
        //Toast.makeText(this, num.toString(), Toast.LENGTH_SHORT).show()
        binding.resultsFor.text = "计算结果 for \"${userInput}\" "
        binding.result.text = "${num}%"
        binding.comment.text = comment
        when (num) {
            in Int.MIN_VALUE..10 -> binding.result.setTextColor(Color.rgb(0x8B, 0x8B, 0x8B)) // 深灰色
            in 11..30 -> binding.result.setTextColor(Color.rgb(0x4C, 0xAF, 0x50)) // 绿色
            in 31..50 -> binding.result.setTextColor(Color.rgb(0xFF, 0xA5, 0x00)) // 橙色
            in 51..70 -> binding.result.setTextColor(Color.rgb(0xFF, 0xC1, 0x07)) // 黄色
            in 71..90 -> binding.result.setTextColor(Color.rgb(0xFF, 0x57, 0x22)) // 橙红色
            else -> binding.result.setTextColor(Color.rgb(0xF4, 0x43, 0x36)) // 红色
        }
    }
}