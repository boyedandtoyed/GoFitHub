package com.example.gofithub

data class Trainer(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var experienceLevel: String = "",
    var specialty: String = "",
    var bio: String = "",
    var profilePicture: String = "",
    var rating: Double = 0.0,
    var hourlyRate: Double = 0.0
)
