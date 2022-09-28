package com.example.anglerhelper

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.navigation.ui.AppBarConfiguration
import android.os.Bundle
import com.example.anglerhelper.R
import androidx.annotation.RequiresApi
import android.os.Build
import android.content.pm.PackageManager
import android.content.Intent
import android.provider.MediaStore
import com.example.anglerhelper.ZoneActivity
import android.graphics.Bitmap
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import android.app.Activity
import android.media.ThumbnailUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

class ZoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zone)
    }
}