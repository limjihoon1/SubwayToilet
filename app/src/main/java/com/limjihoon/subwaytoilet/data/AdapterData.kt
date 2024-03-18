package com.limjihoon.subwaytoilet.data

data class Accc(var header:Header, var body:List<Body>)
data class Header(var resultCnt:Int,var resultCode:String,var resultMsg:String)

data class Body(
    var railOprIsttCd:String,
    var lnCd:String,
    var stinCd:String,
    var grndDvNm:String,
    var stinFlor:Int,
    var gateInotDvNm:String,
    var exitNo:String,
    var dtlLoc:String,
    var mlFmlDvNm: String,
    var toltNum: Int,
    var diapExchNum:String

)