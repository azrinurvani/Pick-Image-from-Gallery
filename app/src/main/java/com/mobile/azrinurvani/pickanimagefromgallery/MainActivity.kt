package com.mobile.azrinurvani.pickanimagefromgallery

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnChooseImage.setOnClickListener {
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request READ_EXTERNAL_STORAGE permission
                    requestPermissions(permissions, PERMISSION_CODE)
                }else{
                    //permission already granted
                    pickImageFromGallery()
                }
            }else{
                //system os is <= Marshmellow
                pickImageFromGallery()
            }
        }

    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            PERMISSION_CODE -> if (grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                pickImageFromGallery()
            }else{
                Toast.makeText(applicationContext,"Permission Denied ",Toast.LENGTH_LONG).show()
            }
        }
    }

    //handle image pick result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode== IMAGE_PICK_CODE){
            imgPreview.setImageURI(data?.data)
        }
    }
}
