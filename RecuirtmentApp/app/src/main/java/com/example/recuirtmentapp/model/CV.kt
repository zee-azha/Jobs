package com.example.recuirtmentapp.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
class CV
(
    @get: Exclude
 var applicationid: String? = null,
 var jobId: String? = null,
 var userId: String? = null,
 var username: String? = null,
 var email: String? = null,
 var phoneNumber: String? = null,
 var cv: String? = null
):Parcelable