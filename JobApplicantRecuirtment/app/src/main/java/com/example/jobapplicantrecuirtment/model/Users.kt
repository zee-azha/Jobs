package com.example.jobapplicantrecuirtment.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize


@Parcelize
data class Users(
    @get: Exclude
    var id: String?,
    var userNama: String?,
    var email: String,
    var password: String?,
    var phoneNumber: String?,
    var alamat: String?

):Parcelable
{
    constructor():this("","","","","","")
}