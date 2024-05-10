package com.newolf.utils.permission.demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.newolf.utils.permission.PermissionUtils;
import com.newolf.utils.permission.demo.databinding.ActivityMainBinding;
import com.newolf.utils.permission.listener.MultiPermissionResultListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * ======================================================================
 *
 * @author : NeWolf
 * @version : 1.0
 * @since :  2024-05-09
 * <p>
 * =======================================================================
 */
public class TestManager {
    private TestManager(){

    }

    private static TestManager _instance = new TestManager();
    @NotNull
    public static TestManager getInstance() {
        return _instance;
    }

    public static final String TAG = "TestManager";

    @SuppressLint("SetTextI18n")
    public void start(FragmentActivity activity, ActivityMainBinding binding){
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        PermissionUtils.INSTANCE.requestMultiplePermissions(activity, new MultiPermissionResultListener() {

            @Override
            public void allGranted() {
                Log.i(TAG, "allGranted: ");
                binding.deniedResult.setText("allGranted");
                binding.explainedResult.setText("");
            }

            @Override
            public void denied(@NonNull List<String> list) {
                Log.i(TAG, "denied: " + list.toString());
                binding.deniedResult.setText("deniedResult: "+list.toString());
            }

            @Override
            public void explained(@NonNull List<String> list) {
                Log.i(TAG, "explained: " + list.toString());
                binding.deniedResult.setText("");
                binding.explainedResult.setText( "explainedResult: "+ list.toString());
            }
        }, permissions);
    }
}
