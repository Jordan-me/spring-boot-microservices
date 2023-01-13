package com.example.rsockettickermanagementserviceconsumer

class PaginationBoundary() {
    var size:Int = 20
    var page:Int = 0

    constructor(size:Int, page:Int):this(){
        this.size = size
        this.page = page
    }

}