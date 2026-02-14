package dev.yllty.fiveidnumforandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.yllty.fiveidnumforandroid.databinding.ActivityMainBinding
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = checkNotNull(_binding) { "Activity has been destroyed" }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        //binding.textView.text = "Hello, Basic Activity!"
        Toast.makeText(this, "这是提示消息", Toast.LENGTH_SHORT).show()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}