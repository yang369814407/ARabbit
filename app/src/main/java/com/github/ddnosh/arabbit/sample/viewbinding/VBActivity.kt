package com.github.ddnosh.arabbit.sample.viewbinding;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ddnosh.arabbit.sample.databinding.ActivityVbBinding
import com.github.ddnosh.arabbit.util.ToastUtil

// viewBinding已经替代Kotlin Android Extensions
class VBActivity : AppCompatActivity() {
    private val TAG = "app_start_timer"
    private lateinit var viewBinding: ActivityVbBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityVbBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initView()
    }

    private fun initView() {
        //多页面状态
        viewBinding.vb.setOnClickListener {
            ToastUtil.showToast("this is viewbinding")
        }
    }
}
