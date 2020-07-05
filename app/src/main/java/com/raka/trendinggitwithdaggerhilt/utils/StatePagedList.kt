package com.raka.trendinggitwithdaggerhilt.utils

import com.raka.myapplication.data.model.State

data class StatePagedList(val status: State, val message:String?) {
    companion object{
        fun success():StatePagedList{
            return StatePagedList(State.SUCCESS,"")
        }
        fun error(msg:String):StatePagedList{
            return StatePagedList(State.FAIL,msg)
        }
        fun loading():StatePagedList{
            return StatePagedList(State.LOADING,"")
        }
        fun idle():StatePagedList{
            return StatePagedList(State.IDLE,"")
        }
    }
}