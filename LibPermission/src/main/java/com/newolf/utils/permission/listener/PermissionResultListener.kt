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
interface PermissionResultListener {
    /**
     * 授予的权限
     *
     * @param permission 对应的权限
     */
    fun granted(permission: String)

    /**
     * 拒绝的权限
     *
     * @param permission 对应的权限
     */
    fun denied(permission: String)

    /**
     * 拒绝且不再询问的权限
     *
     * @param permission 对应的权限
     */
    fun explained(permission: String)
}