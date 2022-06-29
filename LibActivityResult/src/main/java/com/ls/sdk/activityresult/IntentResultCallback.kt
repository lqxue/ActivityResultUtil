package com.ls.sdk.activityresult

import android.content.Intent

/**
 * Create by lqx on 2021-8-3
 * startActivityForResult的回调
 */
interface IntentResultCallback {

    /**
     * 上个Activity 关闭前  设置的结果  最后会传递到finishWithIntent方法中
     * <pre>
     *      setResult(RESULT_OK, new Intent());
     *      finish();
     * </pre>
     */
    fun finishWithResult(data: Intent?)

    /**
     * 用户正常返回  正常关闭页面
     * 或者用户没有setResult  的返回
     */
    fun finishNoResult()
}