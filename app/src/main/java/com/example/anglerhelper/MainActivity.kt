package com.example.anglerhelper

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

//

class MainActivity : AppCompatActivity() {

    companion object{
        private  const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    var imageSize = 100
    private val appBarConfiguration: AppBarConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //take photo
        button_Camera.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE)
            }
        }

        //choose picture from gallery
        button_Gallery!!.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 3)
        }

        //go to fishing zone activity
        button_FishingZone!!.setOnClickListener {
            val intent = Intent(this@MainActivity, ZoneActivity::class.java)
            startActivity(intent)
        }
    }

    /*fun classifyImage(image: Bitmap?) {
        try {
            val model: Model = Model.newInstance(getApplicationContext())

            // Creates inputs for reference.
            val inputFeature0: TensorBuffer =
                TensorBuffer.createFixedSize(intArrayOf(1, 100, 100, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(
                intValues,
                0,
                image.getWidth(),
                0,
                0,
                image.getWidth(),
                image.getHeight()
            )
            var pixel = 0
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++]
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: Model.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
            val confidences: FloatArray = outputFeature0.getFloatArray()
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf(
                "brooktrout",
                "brownbullhead",
                "channelcatfish",
                "lakewhitefish",
                "pumpkinseed",
                "sauger",
                "whitebass",
                "yellowperch"
            )
            result.setText(classes[maxPos])


            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }*/

    //get camera permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0]  == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }else{
                Toast.makeText(this,"Oops you just denied the permission for camera",Toast.LENGTH_LONG).show()
            }
        }
    }


    //get result from the intent of camera or gallery and classify the photo
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

            //if activity result code is same as camera request code, then display the photo in imageView
            //and classify it
            if (requestCode == CAMERA_REQUEST_CODE) {
                var image: Bitmap = intent?.extras?.get("data") as android.graphics.Bitmap

                //set dimension of the photo
                val dimension: Int = image.width.coerceAtMost(image.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                imageView_Image!!.setImageBitmap(image)
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                //classifyImage(image)
            }

            //if activity result code is same as gallery request code, then go to gallery to get a photo and set
            //it in imageview and classify it
            else {
                val dat: Uri? = intent?.data
                /*var image: Bitmap? = null*/
                /*try {
                    image = MediaStore.Images.Media.getBitmap(this.contentResolver, dat)
                } catch (e: IOException) {
                    e.printStackTrace()
                }*/
                var image: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dat) as android.graphics.Bitmap
                val dimension: Int = image.width.coerceAtMost(image.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                imageView_Image!!.setImageBitmap(image)
                image = image?.let { Bitmap.createScaledBitmap(it, imageSize, imageSize, false) }!!
                //classifyImage(image)
            }
    }
}