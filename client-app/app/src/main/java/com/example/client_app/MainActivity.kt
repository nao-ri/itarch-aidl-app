package com.example.client_app

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.service_app.IMyAidlInterface
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    @SuppressLint("Range", "SetTextI18n")
    override fun onStart() {
        super.onStart()
        if(iRemoteService == null){
            val it  = Intent("MyRemoteService")
            it.setPackage("com.example.service_app")
            bindService(it, mConnection, Context.BIND_AUTO_CREATE)
        }

        button.setOnClickListener {
            try {
                val color= iRemoteService?.randomColor()
                Log.d("TAG","$color")
                var base_color= color?.get(0)as Int
                var inv_color= color?.get(1)as Int
                var half_color=color?.get(2)as Int

                constraintLayout.setBackgroundColor(base_color!!)
                view3.setBackgroundColor(inv_color!!)
                view5.setBackgroundColor(half_color!!)
                textView4.text = "Base #" + String.format("%X", base_color).substring(2)
                textView3.text = "Half #" + String.format("%X", half_color).substring(2)
                textView.text = "Inv #" + String.format("%X", inv_color).substring(2)

                //中間色を計算する
//                var red_half=(Color.red(base_color)- Color.red(inv_color))/2
//                var green_half=(Color.green(base_color)- Color.green(inv_color))/2
//                var blue_half=(Color.blue(base_color)- Color.blue(inv_color))/2
//
//                view5.setBackgroundColor( Color.argb(255, red_half, green_half, blue_half))

//                constraintLayout.setBackgroundColor(color!!)
//
//
//                //ソート用のリストを作る
//                var list_color= mutableListOf(Color.red(color), Color.green(color), Color.blue(color))
//                //ソートと最大値と最小値の取り出し
//                list_color.sort()
//                var total_value=list_color[0]+list_color[2]
//                //最大値を255に変更
//                if(total_value>255){
//                    total_value=255
//                }
//                view3.setBackgroundColor(Color.argb(255, total_value- Color.red(color), total_value- Color.green(color), total_value- Color.blue(color)))
////                view3.setBackgroundColor(Color.argb(255, 555, total_value-Color.green(color), total_value-Color.blue(color)))
//                Log.d("TAG","$test")
//                Log.d("TAG","$list_color")
////                button.setBackgroundColor(Color.argb(255, (0..255).random(), (0..255).random(), (0..255).random()))
//                textView.text = "#" + String.format("%X", color).substring(2)

            } catch (e : RemoteException) {
                e.printStackTrace()
            }
        }
    }
    var iRemoteService : IMyAidlInterface? = null

    val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            iRemoteService = IMyAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(className: ComponentName?) {
            Log.e("ClientApplication", "Service has unexpectedly disconnected")
            iRemoteService = null
        }
    }
}