package com.grad.dawinii.util

import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class PermissionHandler(private val fragment: Fragment) : EasyPermissions.PermissionCallbacks {
    fun checkCameraPermission(host: Fragment) {
        if(hasCameraPermission()){
            return
        }
        EasyPermissions.requestPermissions(host,
            "Please Allow Camera Permission",
            100,
            android.Manifest.permission.CAMERA)
    }

    private fun hasCameraPermission() = EasyPermissions.hasPermissions(fragment.requireContext(), android.Manifest.permission.CAMERA)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(fragment, perms)){
            AppSettingsDialog.Builder(fragment).build().show()
        }else{
            checkCameraPermission(fragment)
        }
    }


}