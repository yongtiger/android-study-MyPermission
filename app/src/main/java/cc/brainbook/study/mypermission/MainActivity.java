package cc.brainbook.study.mypermission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";

    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    private static final int ACTION_APPLICATION_DETAILS_SETTINGS_REQUEST_CODE = 1;
    private static final int ACTION_WIFI_SETTINGS_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "============== MainActivity# onCreate()# ==============");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ///[Android M 6.0 (API level 23)及以上版本必须动态设置权限]
        ///申请权限（强制获得权限，否则finish()关闭本页面）
        if (requestPermission()) {
            ///初始化
            init();
        }
    }

    ///[Android M 6.0 (API level 23)及以上版本必须动态设置权限]
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "MainActivity# onRequestPermissionsResult()# requestCode: " + requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ///初始化
                    init();
                } else {
                    Toast.makeText(this,"您拒绝了SD卡写入权限，请开通", Toast.LENGTH_SHORT).show();

                    ///用户未选择任何（同意、拒绝、不再询问）时为false，第一次用户拒绝后为true，直到用户点击不再询问时为false
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ///开启本APP应用的设置页面
                        startApplicationDetailsSettingsActivity();
                    }

                    ///关闭本页面
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "MainActivity# onStart()# ");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "MainActivity# onResume()# ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "MainActivity# onPause()# ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "MainActivity# onStop()# ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "============== MainActivity# onDestroy()# ==============");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "MainActivity# onActivityResult()# requestCode: " + requestCode + ", resultCode: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ACTION_APPLICATION_DETAILS_SETTINGS_REQUEST_CODE:
                ///刷新本页面
                recreate();// 直接调用Activity的recreate()方法重启Activity
                break;
            case ACTION_WIFI_SETTINGS_REQUEST_CODE:
                ///刷新本页面
                recreate();// 直接调用Activity的recreate()方法重启Activity
                break;

        }
    }

    ///[Android M 6.0 (API level 23)及以上版本必须动态设置权限]
    /**
     * 申请权限（强制获得权限，否则否则finish()关闭本页面）
     *
     * @return
     */
    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            ///用户未选择任何（同意、拒绝、不再询问）时为false，第一次用户拒绝后为true，直到用户点击不再询问时为false
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("本应该需要权限：SD卡写入权限")
                        .setMessage("原因：保存APK下载的文件")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ///请求WRITE_EXTERNAL_STORAGE权限
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ///关闭本页面
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ///请求WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 开启本APP应用的设置页面
     */
    private void startApplicationDetailsSettingsActivity() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, ACTION_APPLICATION_DETAILS_SETTINGS_REQUEST_CODE);
    }

    /**
     * 开启Wifi网络设置页面
     */
    private void startWifiSettingsActivity() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivityForResult(intent, ACTION_WIFI_SETTINGS_REQUEST_CODE);
    }

    /**
     * 初始化过程
     */
    private void init() {
        Log.d(TAG, "MainActivity# init()# ");

        // todo ...

    }

}
