package com.ls.sdk.activityresult

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import java.util.*

/**
 * Create by lqx on 2021-8-3
 * 把OnActivityResult方式转换为Callback方式
 */
class ResultHandleAndroidXFragment : Fragment() {
    /**
     * ActivityResultCallback引用着activity或者Fragment,当启动的activity执行startActivityForResult后马上销毁,
     * 此时RouterFragmentV4也跟着销毁mCallbacks也就被回收了,整个过程没有被rootObject引用着所有不会存在泄漏
     */
    private val mCallbacks = SparseArray<IntentResultCallback>()

    private val mCodeGenerator = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun startActivityForResult(intent: Intent?, callback: IntentResultCallback) {
        val requestCode = makeRequestCode()
        mCallbacks.put(requestCode, callback)
        startActivityForResult(intent, requestCode)
    }

    /**
     * 随机生成唯一的requestCode，最多尝试10次
     *
     * @return
     */
    private fun makeRequestCode(): Int {
        var requestCode: Int
        var tryCount = 0
        do {
            requestCode = mCodeGenerator.nextInt(0x0000FFFF)
            tryCount++
        } while (mCallbacks.indexOfKey(requestCode) >= 0 && tryCount < 10)
        return requestCode
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val callback = mCallbacks[requestCode]
        mCallbacks.remove(requestCode)
        if (callback != null) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                callback.finishWithResult(data)
            } else {
                callback.finishNoResult()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): ResultHandleAndroidXFragment {
            return ResultHandleAndroidXFragment()
        }
    }
}