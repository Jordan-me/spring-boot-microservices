package com.example.rsockettickermanagementserviceconsumer

//import com.fasterxml.jackson.annotation.JsonCreator

class ExternalReferenceBoundary() {
    var system: String? = null
    var externalSystemId: String? = null

    constructor(system:String,
                externalSystemId:String):this(){
        this.system = system
        this.externalSystemId = externalSystemId
    }

}
