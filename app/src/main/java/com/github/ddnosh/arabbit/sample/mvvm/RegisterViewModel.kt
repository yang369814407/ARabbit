package com.github.ddnosh.arabbit.sample.mvvm

import androidx.lifecycle.MutableLiveData
import com.github.ddnosh.arabbit.util.RxUtil.io2Main
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

//使用Android Jetpack组件: ViewModel（Kotlin）
class RegisterViewModel : AutoDisposeViewModel() {
    fun register(): MutableLiveData<Long> {
        val registerData = MutableLiveData<Long>()

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .compose(io2Main())
                .autoDisposable(this)
                .subscribe({ registerData.setValue(it) }) { registerData.setValue(-1) }

        return registerData
    }
}