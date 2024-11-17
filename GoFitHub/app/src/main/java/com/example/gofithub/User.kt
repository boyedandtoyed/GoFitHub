package com.example.gofithub

data class User(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var dob: String = "",
    var weight: Double = 0.0,
    var height: Double = 0.0,
    var goalType: String = "",
    var bio: String = "",
    var profilePicture: String = ""
)
