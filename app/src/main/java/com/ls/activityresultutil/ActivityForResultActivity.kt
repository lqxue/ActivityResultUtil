package com.ls.activityresultutil

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ActivityForResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //使用该MV
        val intent = Intent()
        intent.putExtra("name","小丽")
        setResult(RESULT_OK, intent)
        finish()
    }
}