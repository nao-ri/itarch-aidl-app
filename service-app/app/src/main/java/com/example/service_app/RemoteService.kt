package com.example.service_app

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.IBinder

class RemoteService : Service() {

    override fun onBind(intent: Intent): IBinder {

        return binder
    }
    private val binder = object : IMyAidlInterface.Stub() {
        override fun randomColor(): List<Int> {
            //ベースとなる色を生成
            val base_color =Color.argb(255, (50..255).random(), (50..255).random(), (50..255).random())

            //補色を計算
            //ソート用のリストを作る
            var list_color= mutableListOf(Color.red(base_color), Color.green(base_color), Color.blue(base_color))
            //ソートと最大値と最小値を合計
            list_color.sort()
            var total_value=list_color[0]+list_color[2]
            //最大値を255に補正
            if(total_value>255){
                total_value=255
            }
            var inversion_color=Color.argb(255, total_value- Color.red(base_color), total_value- Color.green(base_color), total_value- Color.blue(base_color))

            //ベースと補色から中間色を計算
            var red_half=(Color.red(base_color)- Color.red(inversion_color))/2
            var green_half=(Color.green(base_color)- Color.green(inversion_color))/2
            var blue_half=(Color.blue(base_color)- Color.blue(inversion_color))/2
            var half_color=Color.argb(255, red_half, green_half, blue_half)

//base 補色　中間色を返す
            val list: List<Int> = arrayListOf(base_color,inversion_color,half_color)
            return list
        }
    }
}
