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
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.anglerhelper.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private var binding: FragmentSecondBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.buttonSecond.setOnClickListener {
            NavHostFragment.findNavController(this@SecondFragment)
                .navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}