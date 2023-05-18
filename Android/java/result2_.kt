package com.example.ufoer

import java.io.Serializable

class result2_: Serializable {

    var name = ""
    var eng = ""
    var math = ""

    constructor(name: String, eng: String, math: String) {
        this.name = name
        this.eng = eng
        this.math = math
    }

}