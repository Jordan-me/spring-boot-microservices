package com.example.rsockettickermanagementservice

class CompanyBoundary {
    var company: String? = null
    var size:Int = 10
    var page:Int = 0

    override fun toString(): String {
        return "CompanyBoundary(company=$company)"
    }
}