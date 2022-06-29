package com.ls.sdk.activityresult

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.launcher.ARouter

/**
 *  Create by lqx on 2021-8-3
 *  startActivityForResult 跳转封装类，把OnActivityResult方式改为Callback方式
 */
class StartActivityResultCompact {

    /**
     * 统一返回结果(一般情况只有一个返回值)
     */
    companion object{
        private const val FRAGMENT_TAG = "StartActivityResultCompact"

        private fun getRouterFragmentX(activity: FragmentActivity): ResultHandleAndroidXFragment {
            var routerFragment = activity.supportFragmentManager
                .findFragmentByTag(FRAGMENT_TAG) as ResultHandleAndroidXFragment?
            if (routerFragment == null) {
                routerFragment = ResultHandleAndroidXFragment.newInstance()
                val fragmentManager = activity.supportFragmentManager
                fragmentManager.beginTransaction().add(routerFragment, FRAGMENT_TAG).commitAllowingStateLoss()
                fragmentManager.executePendingTransactions()
            } else if (routerFragment.isDetached) {
                val fragmentManager = activity.supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.attach(routerFragment).commitAllowingStateLoss()
                fragmentManager.executePendingTransactions()
            }
            return routerFragment
        }

        /**
         * @param activity
         * @param intent
         * @param callback
         * @param <T>
         */
        fun startActivityForResult(activity: FragmentActivity, routerName: String, bundle: Bundle = Bundle(), callback: IntentResultCallback) {
            try {
                val postcard = ARouter.getInstance()
                    .build(routerName)
                    .with(bundle)
                LogisticsCenter.completion(postcard)
                val resultHandleAndroidXFragment =
                    getRouterFragmentX(activity)

                val intent = Intent(activity, postcard.destination)
                intent.putExtras(postcard.extras)
                resultHandleAndroidXFragment.startActivityForResult(intent, callback);
            } catch (e: Exception) {
                //当不存在路由的时候会报错直接调用无结果回调
                callback.finishNoResult()
            }
        }

        /**
         * @param activity
         * @param intent
         * @param callback
         * @param <T>
         */
        fun startActivityForResult(activity: FragmentActivity, className : Class<*>, bundle: Bundle = Bundle(), callback: IntentResultCallback) {
            try {
                val resultHandleAndroidXFragment =
                    getRouterFragmentX(activity)

                val intent = Intent(activity, className)
                intent.putExtras(bundle)
                resultHandleAndroidXFragment.startActivityForResult(intent, callback);
            } catch (e: Exception) {
                callback.finishNoResult()
            }
        }

        /**
         * @param activity
         * @param intent
         * @param callback
         * @param <T>
         */
        fun startActivityForResult(activity: FragmentActivity, intent: Intent, bundle: Bundle = Bundle(), callback: IntentResultCallback) {
            try {
                val resultHandleAndroidXFragment =
                    getRouterFragmentX(activity)

                intent.putExtras(bundle)
                resultHandleAndroidXFragment.startActivityForResult(intent, callback);
            } catch (e: Exception) {
                callback.finishNoResult()
            }
        }
    }
}