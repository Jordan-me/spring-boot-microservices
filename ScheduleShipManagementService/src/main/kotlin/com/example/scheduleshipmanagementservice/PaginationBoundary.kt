package com.example.scheduleshipmanagementservice

class PaginationBoundary {
    var page:Int = 0
    var size:Int = 20
    var filterType:String? = null
    var filterValue:String? = null
    var sortBy:String? = null
    var sortOrder:String? = "ASC"
}