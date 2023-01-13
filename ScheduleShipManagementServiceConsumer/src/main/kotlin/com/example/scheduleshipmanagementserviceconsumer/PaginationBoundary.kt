package com.example.scheduleshipmanagementserviceconsumer

class PaginationBoundary() {
    var page:Int = 0
    var size:Int = 20
    var filterType:String? = null
    var filterValue:String? = null
    var sortAttribute:String? = null
    var sortOrder:String? = "ASC"

    constructor(filterType:String, filterValue:String,sortAttribute:String,sortOrder:String,size:Int, page:Int):this(){
        this.filterType = filterType
        this.filterValue = filterValue
        this.sortAttribute = sortAttribute
        this.sortOrder = sortOrder
        this.size = size
        this.page = page
    }

    constructor(sortAttribute:String,sortOrder:String,size:Int, page:Int):this(){
        this.sortAttribute = sortAttribute
        this.sortOrder = sortOrder
        this.size = size
        this.page = page
    }
    constructor(size:Int, page:Int):this(){
        this.size = size
        this.page = page
    }
}