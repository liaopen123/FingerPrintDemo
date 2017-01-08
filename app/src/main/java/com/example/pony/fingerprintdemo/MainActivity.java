package com.example.pony.fingerprintdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    private FingerprintManager fingerprintManager;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fingerprintManager = getSystemService(FingerprintManager.class);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        
        
        
        if (fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints()) {
            fingerprintManager.authenticate(null, null, 0, callback, null);
        }
    }



    private FingerprintManager.AuthenticationCallback callback = new FingerprintManager.AuthenticationCallback() {
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            //指纹验证成功
            Toast.makeText(MainActivity.this, "onAuthenticationSucceeded()", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            //指纹验证失败，不可再验
            Toast.makeText(MainActivity.this, "onAuthenticationError()"+ errString, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            //指纹验证失败，可再验，可能手指过脏，或者移动过快等原因。
            Toast.makeText(MainActivity.this, "onAuthenticationHelp()"+ helpString, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onAuthenticationFailed() {
            //指纹验证失败，指纹识别失败，可再验，该指纹不是系统录入的指纹。
            Toast.makeText(MainActivity.this, "onAuthenticationFailed():不能识别", Toast.LENGTH_SHORT).show();
        }
    };
}
