package com.newolf.utils.permission.demo

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.newolf.utils.permission.PermissionUtils
import com.newolf.utils.permission.listener.PermissionResultListener

/**
 * ======================================================================
 *
 *
 * @author : NeWolf
 * @version : 1.0
 * @since :  2024-05-10
 *
 * =======================================================================
 */
class HomeFragment:Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvResult = view.findViewById<MaterialTextView>(R.id.tv_result)

        view.findViewById<MaterialButton>(R.id.request_permission).setOnClickListener {
            PermissionUtils.requestPermission(this, Manifest.permission.RECORD_AUDIO, object :
                PermissionResultListener {
                override fun granted(permission: String) {
                    //防止太长体验不好，真实使用时无需做此操作
                    val result = permission.replace("android.permission.", "")
                    with(tvResult) {
                        text = getString(R.string.result_granted, result)
                    }
                }

                override fun denied(permission: String) {
                    val result = permission.replace("android.permission.", "")
                    with(tvResult) {
                        text = getString(R.string.result_denied, result)
                    }
                }

                override fun explained(permission: String) {
                    val result = permission.replace("android.permission.", "")
                    with(tvResult) {
                        text = getString(R.string.result_explained, result)
                    }
                }


            })

        }
    }
}