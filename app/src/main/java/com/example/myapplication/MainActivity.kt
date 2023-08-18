package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.safetynet.SafetyNetApi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks {
//class MainActivity : AppCompatActivity() {

    private lateinit var btnClick: Button
    private lateinit var checkBox: CheckBox
    private val siteKey = "6LdjOrYnAAAAAFEcmN8YGsGhJxeGy2zufBZXhjnZ"
    private lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBox = findViewById(R.id.checkbox)
        btnClick = findViewById(R.id.btnClick)

        googleApiClient =
            GoogleApiClient.Builder(this).addApi(SafetyNet.API).addConnectionCallbacks(this).build()

//        checkBox.setOnClickListener {
//            if (checkBox.isChecked) {
//                SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient, siteKey).setResu
//            }
//        }
        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                SafetyNet.getClient(this).verifyWithRecaptcha(siteKey)
                    .addOnCompleteListener(
                        this,
                        OnCompleteListener { task: Task<SafetyNetApi.RecaptchaTokenResponse?> ->
                            if (task.isSuccessful) {
                                // reCAPTCHA verification successful
                                val result = task.result
                                val userResponseToken = result?.tokenResult
                                if (userResponseToken != null && userResponseToken.isNotEmpty()) {
                                    // Proceed with your logic
                                    checkBox.text =
                                        "Captcha verification successful, proceed with your logic"
                                }
                            } else {
                                // reCAPTCHA verification failed
                                checkBox.text = "Captcha verification failed"

                            }
                        })
            }
        }

//        btnClick.setOnClickListener {
//            SafetyNet.getClient(this).verifyWithRecaptcha(siteKey)
//                .addOnSuccessListener { response ->
//                    if (response.tokenResult?.isNotBlank() == true) {
//                        // Captcha verification successful, proceed with your logic
//                        checkBox.text = "Captcha verification successful, proceed with your logic"
//                    } else {
//                        // Captcha verification failed
//                        checkBox.text = "Captcha verification failed"
//
//                    }
//                }
//                .addOnFailureListener { e ->
//                    // Error occurred during captcha verification
//                    checkBox.text = "Error occurred during captcha verification"
//
//                }
//        }
    }

    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {
    }
}