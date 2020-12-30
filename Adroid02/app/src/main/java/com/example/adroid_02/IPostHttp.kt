package com.example.adroid_02

class IPostHttp(
    val id: Int,
    var userId: Any,
    val title: String,
    var body: String
) {
    var userIdTransformado:Int = 0
    init{
        if(userId is String){
            userId = userId.toString().toInt()
        }
        if(userId is Int){
            userId = userId.toString()
        }
    }
}