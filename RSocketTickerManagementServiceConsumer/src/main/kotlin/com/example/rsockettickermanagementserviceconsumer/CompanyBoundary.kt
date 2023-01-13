package com.example.rsockettickermanagementserviceconsumer

class CompanyBoundary() {
    var company: String? = null
    var size:Int = 10
    var page:Int = 0

    constructor(company:String,
                size:Int, page:Int):this(){
        this.company = company
        this.size = size
        this.page = page
    }

    override fun toString(): String {
        return "CompanyBoundary(company=$company)"
    }
}