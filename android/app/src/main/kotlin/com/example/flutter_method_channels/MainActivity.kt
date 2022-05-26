package com.example.flutter_method_channels

import android.content.*
import io.flutter.embedding.android.FlutterActivity
import androidx.annotation.NonNull
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.content.Intent

class MainActivity: FlutterActivity() {
    private val BATTERY_CHANNEL="xuwudawei/battery"
    private lateinit var channel: MethodChannel

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        channel= MethodChannel(flutterEngine.dartExecutor.binaryMessenger,BATTERY_CHANNEL)

        //Receive data from flutter
        channel.setMethodCallHandler{ call,result ->
            if (call.method == "getBatteryLevel") {
                val batteryLevel = getBatteryLevel()
                val name=call.argument<String>("name")
                if (batteryLevel != -1) {
                    result.success("$name says: $batteryLevel%")
                } else {
                    result.error("UNAVAILABLE", "Battery level not available.", null)
                }
            } else {
                result.notImplemented()
            }
        }
    }

    private fun getBatteryLevel():Int{
        val batteryLevel:Int
        if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP){
            val batteryManager=getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryLevel=batteryManager.getIntProperty((BatteryManager.BATTERY_PROPERTY_CAPACITY))

        }else{
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }
        return batteryLevel
    }
}
