package com.example.recuirtmentapp.model

data class Company(
    var id: String,
    var companyName: String?,
    var email: String,
    var password: String?,
    var phoneNumber: String?,
    var alamat: String?
)
{
    constructor():this("","","","","","")
}

