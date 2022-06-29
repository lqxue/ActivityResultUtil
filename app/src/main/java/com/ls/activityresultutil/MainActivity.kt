package com.ls.activityresultutil

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ls.sdk.activityresult.IntentResultCallback
import com.ls.sdk.activityresult.StartActivityResultCompact

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "audio/*")
        StartActivityResultCompact.startActivityForResult(activity = this,
                intent = intent,
        callback = object : IntentResultCallback{
            override fun finishWithResult(data: Intent?) {
                var imgUri = data?.data
                Toast.makeText(this@MainActivity,imgUri.toString(), Toast.LENGTH_LONG).show()
            }

            override fun finishNoResult() {

            }
        })
    }
}