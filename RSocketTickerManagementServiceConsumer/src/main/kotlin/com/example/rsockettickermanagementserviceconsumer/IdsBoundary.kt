package com.example.rsockettickermanagementserviceconsumer

class IdsBoundary() {
    var tickerIds:MutableList<String>? = null

    constructor(tickerIds:MutableList<String>):this(){
        this.tickerIds = tickerIds
    }
}