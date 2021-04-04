package com.github.ddnosh.arabbit.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ddnosh.arabbit.module.rxbus.RxBus
import com.github.ddnosh.arabbit.sample.base.BaseActivity
import com.github.ddnosh.arabbit.sample.binding.databinding.DBActivity
import com.github.ddnosh.arabbit.sample.coroutine.CoroutineActivity
import com.github.ddnosh.arabbit.sample.dialog.DialogActivity
import com.github.ddnosh.arabbit.sample.event.TestEvent
import com.github.ddnosh.arabbit.sample.image.GlideActivity
import com.github.ddnosh.arabbit.sample.livedata.LiveDataActivity
import com.github.ddnosh.arabbit.sample.mvvm.LoginActivity
import com.github.ddnosh.arabbit.sample.network.NetworkActivity
import com.github.ddnosh.arabbit.sample.util.TimeUtils
import com.github.ddnosh.arabbit.sample.binding.viewbinding.VBActivity
import com.github.ddnosh.arabbit.sample.multiplestatusview.MSVActivity
import com.github.ddnosh.arabbit.sample.other.SampleAdapter
import com.github.ddnosh.arabbit.ui.adapter.CommonAdapter
import com.github.ddnosh.arabbit.ui.adapter.CommonViewHolder
import com.github.ddnosh.arabbit.util.LogUtil
import com.github.ddnosh.arabbit.util.RxUtil
import com.trello.rxlifecycle3.kotlin.bindUntilEvent
import kotlinx.android.synthetic.main.activity_main.*

/*
    1. 使用Kotlin Android Extensions代替ButterKnife和findViewById
    2. 目前Kotlin Android Extensions已经被viewBinding替代
 */
class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"

    private var nameList = arrayListOf("1.MVVM", "2.LiveData", "3.Coroutine", "4.Network", "5.Image", "6.Dialog", "7.ViewBinding", "8.DataBinding", "9.MultipleStatusView")

    override val contentViewLayoutID: Int = R.layout.activity_main

    @SuppressLint("AutoDispose")
    override fun initViewsAndEvents(savedInstanceState: Bundle?) {
        super.initViewsAndEvents(savedInstanceState)
        //RxBus
        RxBus.getDefault().toObservable(TestEvent::class.java)
                .bindLifeOwner(this)
                .compose(RxUtil.io2Main())
                .bindUntilEvent(lifecycleProvider, Lifecycle.Event.ON_DESTROY)
                .subscribe {
                    LogUtil.d(TAG, "[RxBus] ${it.data}")
                }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = object : CommonAdapter<String>(this, R.layout.sample_item, nameList) {
            override fun convert(holder: CommonViewHolder, name: String) {
                holder.setText(R.id.sample_text, name)
                holder.setOnClickListener(R.id.item_root) {
                    LogUtil.d(TAG, "onItemClick:$name")
                    goto(name)
                }
            }
        }
    }

    private fun goto(name: String) {
        when (name) {
            "1.MVVM" -> readyGo(LoginActivity::class.java)
            "2.LiveData" -> readyGo(LiveDataActivity::class.java)
            "3.Coroutine" -> readyGo(CoroutineActivity::class.java)
            "4.Network" -> readyGo(NetworkActivity::class.java)
            "5.Image" -> readyGo(GlideActivity::class.java)
            "6.Dialog" -> readyGo(DialogActivity::class.java)
            "7.ViewBinding" -> readyGo(VBActivity::class.java)
            "8.DataBinding" -> readyGo(DBActivity::class.java)
            "9.MultipleStatusView" -> readyGo(MSVActivity::class.java)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            val hotStartTime = TimeUtils.getTimeCalculate(TimeUtils.HOT_START);
            Log.d(TAG, "热启动时间:$hotStartTime")
            if (TimeUtils.sColdStartTime > 0 && hotStartTime > 0) {
                // 真正的冷启动时间 = Application启动时间 + 热启动时间
                val coldStartTime = TimeUtils.sColdStartTime + hotStartTime;
                Log.d(TAG, "application启动时间:" + TimeUtils.sColdStartTime)
                Log.d(TAG, "冷启动时间:$coldStartTime")
                // 过滤掉异常启动时间
                if (coldStartTime < 50000) {
                    // 上传冷启动时间coldStartTime
                }
            } else if (hotStartTime > 0) {
                // 过滤掉异常启动时间
                if (hotStartTime < 30000) {
                    // 上传热启动时间hotStartTime
                }
            }
        }
    }
}