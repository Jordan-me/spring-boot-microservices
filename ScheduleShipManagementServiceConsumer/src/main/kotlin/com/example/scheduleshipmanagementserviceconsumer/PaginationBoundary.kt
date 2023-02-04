package com.example.scheduleshipmanagementserviceconsumer

class PaginationBoundary() {
    var page:Int = 0
    var size:Int = 20
    var filterType:String? = null
    var filterValue:String? = null
    var sortBy:String? = null
    var sortOrder:String? = "ASC"

    constructor(filterType:String, filterValue:String,sortBy:String,sortOrder:String,size:Int, page:Int):this(){
        this.filterType = filterType
        this.filterValue = filterValue
        this.sortBy = sortBy
        this.sortOrder = sortOrder
        this.size = size
        this.page = page
    }

    constructor(sortBy:String,sortOrder:String,size:Int, page:Int):this(){
        this.sortBy = sortBy
        this.sortOrder = sortOrder
        this.size = size
        this.page = page
    }
    constructor(size:Int, page:Int):this(){
        this.size = size
        this.page = page
    }
}