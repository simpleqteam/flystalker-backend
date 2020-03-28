package xyz.simpleq.flystalker.service

class HttpRequest{
    var method: HttpMethod = HttpMethod.GET
    var headers: Map<String, String> = mapOf()
    var params: Map<String, String> = mapOf()
    var url:String = ""
    var time: Long = 0
}