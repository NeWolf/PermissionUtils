package com.newolf.utils.permission.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.newolf.utils.permission.PermissionUtils
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
class PermissionRequestFragment : Fragment() {

    companion object {
        const val TAG = "WolfPermissionRequest"
        const val DENIED = "DENIED"
        const val EXPLAINED = "EXPLAINED"
        fun requestMultiplePermissions(
            listener: MultiPermissionResultListener,
            permissions: Array<String>,
        ): PermissionRequestFragment {
            val fragment = PermissionRequestFragment()
            fragment.setMultiplePermissions(permissions)
            fragment.setMultiPermissionResultListener(listener)
            fragment.setIsMultiple(true)
            return fragment


        }

        fun requestPermission(
            listener: PermissionResultListener,
            permission: String
        ): PermissionRequestFragment {
            val fragment = PermissionRequestFragment()
            fragment.setMultiplePermissions(arrayOf(permission))
            fragment.setPermissionResultListener(listener)
            fragment.setIsMultiple(false)
            return fragment
        }
    }

    fun goByActivity(activity: FragmentActivity) {
        fragmentManager = activity.supportFragmentManager
        if (PermissionUtils.isDebug()) {
            Log.d(TAG, "goByActivity: activity = $activity, fragmentManager = $fragmentManager")
        }
        fragmentManager.beginTransaction().add(this, activity.javaClass.getName())
            .commit()
    }


    fun goByFragment(fragment: Fragment) {
        fragmentManager = fragment.childFragmentManager
        if (PermissionUtils.isDebug()) {
            Log.d(TAG, "goByFragment: fragment = $fragment, fragmentManager = $fragmentManager")
        }
        fragmentManager.beginTransaction().add(this, fragment.javaClass.getName()).commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG, "onAttach context = $context")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG, "onDetach context = $context")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (permissions == null) {
            if (isMultiPermission) {
                multiPermissionListener?.allGranted()
            } else {
                permissionResultListener?.denied("")
            }
            return
        }
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result: Map<String, Boolean> ->

            if (PermissionUtils.isDebug()) {
                result.forEach { entry -> Log.e(TAG, "${entry.key} : ${entry.value}") }
            }
            //过滤 value 为 false 的元素并转换为 list
            val deniedList = result.filter { !it.value }.map { it.key }
            when {
                deniedList.isNotEmpty() -> {
                    //对被拒绝全选列表进行分组，分组条件为是否勾选不再询问
                    val map = deniedList.groupBy { permission ->
                        if (shouldShowRequestPermissionRationale(permission)) DENIED else EXPLAINED
                    }
                    //被拒接且没勾选不再询问
                    map[DENIED]?.let {
                        if (isMultiPermission) {
                            multiPermissionListener?.denied(it)
                        } else {
                            permissionResultListener?.denied(it[0])
                        }
                    }
                    //被拒接且勾选不再询问
                    map[EXPLAINED]?.let {
                        if (isMultiPermission) {
                            multiPermissionListener?.explained(it)
                        } else {
                            permissionResultListener?.explained(it[0])
                        }
                    }
                }

                else -> {
                    if (isMultiPermission) {
                        multiPermissionListener?.allGranted()
                    } else {
                        permissionResultListener?.granted(result.keys.first())
                    }
                }
            }
            fragmentManager.beginTransaction().remove(this).commit()
        }.launch(permissions)

    }


    private var permissionResultListener: PermissionResultListener? = null
    private fun setPermissionResultListener(listener: PermissionResultListener) {
        this.permissionResultListener = listener
    }

    private var permissions: Array<String>? = null
    private fun setMultiplePermissions(permissions: Array<String>) {
        this.permissions = permissions
    }

    private var isMultiPermission = false
    private fun setIsMultiple(isMulti: Boolean) {
        this.isMultiPermission = isMulti
    }


    private var multiPermissionListener: MultiPermissionResultListener? = null
    private fun setMultiPermissionResultListener(listener: MultiPermissionResultListener) {
        this.multiPermissionListener = listener
    }

    private lateinit var fragmentManager: FragmentManager

}