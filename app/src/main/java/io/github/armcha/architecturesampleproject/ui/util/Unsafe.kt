package io.github.armcha.architecturesampleproject.ui.util

import android.util.Log
import com.ironz.unsafe.UnsafeAndroid
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import kotlin.system.measureTimeMillis


data class Hello(val age:Int){
    val array = arrayListOf<String>()
    init {
        repeat(1000){
            array.add("Hello")
        }
    }
}

class Unsafe {

    private val unsafe = UnsafeAndroid()

    fun go(){
        val obj = Hello(23)
        val time = measureTimeMillis {
            repeat(10000){
                shallowCopy(obj)
            }
        }
        Log.e("shallowCopyTime", "${time}")

        val cloneTime = measureTimeMillis {
            repeat(10000){
                obj.copy()
            }
        }
        Log.e("cloneTime", "${cloneTime}")
    }

    private fun shallowCopy(obj: Any): Any? {
        val size = sizeOf(obj)
        val start = toAddress(obj)
        val address = unsafe.allocateMemory(size)
        unsafe.copyMemory(start, address, size)
        return fromAddress(address)
    }

    private fun sizeOf(o: Any): Long {
        val fields = HashSet<Field>()
        var c: Class<*> = o.javaClass
        while (c != Any::class.java) {
            for (f in c.declaredFields) {
                if (f.modifiers and Modifier.STATIC == 0) {
                    fields.add(f)
                }
            }
            c = c.superclass
        }

        // get offset
        var maxSize: Long = 0
        for (f in fields) {
            val offset = unsafe.objectFieldOffset(f)
            if (offset > maxSize) {
                maxSize = offset
            }
        }

        return (maxSize / 8 + 1) * 8   // padding
    }

    private fun toAddress(obj: Any): Long {
        val array = arrayOf(obj)
        val baseOffset = unsafe.arrayBaseOffset(Array<Any>::class.java).toLong()
        return normalize(unsafe.getInt(array, baseOffset))
    }

    private fun fromAddress(address: Long): Any? {
        val array = arrayOf<Any?>(null)
        val baseOffset = unsafe.arrayBaseOffset(Array<Any>::class.java).toLong()
        unsafe.putLong(array, baseOffset, address)
        return array[0]
    }

    private fun normalize(value: Int): Long {
        return if (value >= 0) value.toLong() else 0L.inv().ushr(32) and value.toLong()
    }
}