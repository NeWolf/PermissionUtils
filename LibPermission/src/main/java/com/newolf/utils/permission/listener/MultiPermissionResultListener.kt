package com.newolf.utils.permission.listener

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
interface MultiPermissionResultListener {
    /**
     * 所有权限已授予，可进行对应的操作
     */
    fun allGranted()

    /**
     * 部分权限被拒绝，如列表
     *
     * @param list 权限列表
     */
    fun denied(list: List<String>)

    /**
     *权限被拒绝且不再询问
     *
     * @param list 权限列表
     */
    fun explained(list: List<String>)
}