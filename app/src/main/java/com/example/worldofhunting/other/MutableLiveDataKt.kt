package com.example.worldofhunting.other

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


//NonNull MutableLiveData
class MutableLiveDataKt<T: Any>(initValue: T): MutableLiveData<T>() {

    init {
        value = initValue
    }

    override fun getValue(): T {
        return super.getValue()!!
    }

    override fun setValue(value: T) {
        super.setValue(value)
    }

    fun observe(owner: LifecycleOwner, body: (T) -> Unit) {
        observe(owner, Observer { t -> body(t!!) })
    }

    override fun postValue(value: T) {
        super.postValue(value)
    }
}