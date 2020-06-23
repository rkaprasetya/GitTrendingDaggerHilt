package com.raka.trendinggitwithdaggerhilt.utils

import com.raka.myapplication.data.model.State
import com.raka.myapplication.data.model.State.*

data class Resource<out T>(val status:State,val data:T?, val message:String?) {
    companion object{
        fun<T> success(data:T?):Resource<T>{
            return Resource(SUCCESS,data,null)
        }
        fun<T> error(msg:String,data:T?):Resource<T>{
            return Resource(FAIL,data,msg)
        }
        fun<T> loading(data:T?):Resource<T>{
            return Resource(LOADING,data,null)
        }
    }
}