package com.newolf.utils.permission

import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.newolf.utils.permission.fragment.PermissionRequestFragment
import com.newolf.utils.permission.listener.MultiPermissionResultListener
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
object PermissionUtils {
    private var isLog = BuildConfig.DEBUG

    fun debugLog(enable: Boolean) {
        isLog = enable
    }

    fun isDebug(): Boolean {
        return isLog
    }


    fun requestPermission(
        activity: FragmentActivity,
        permission: String,
        listener: PermissionResultListener
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            listener.granted(permission)
            return
        }
        PermissionRequestFragment.requestPermission(listener, permission)
            .goByActivity(activity)
    }

    fun requestPermission(
        fragment: Fragment,
        permission: String,
        listener: PermissionResultListener
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            listener.granted(permission)
            return
        }
        PermissionRequestFragment.requestPermission(listener, permission)
            .goByFragment(fragment)
    }


    fun requestMultiplePermissions(
        activity: FragmentActivity,
        listener: MultiPermissionResultListener,
        permissions: Array<String>
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || permissions.isEmpty()) {
            listener.allGranted()
            return
        }
        PermissionRequestFragment.requestMultiplePermissions(listener, permissions)
            .goByActivity(activity)
    }

    fun requestMultiplePermissions(
        fragment: Fragment,
        listener: MultiPermissionResultListener,
        permissions: Array<String>
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || permissions.isEmpty()) {
            listener.allGranted()
            return
        }
        PermissionRequestFragment.requestMultiplePermissions(listener, permissions)
            .goByFragment(fragment)
    }
}