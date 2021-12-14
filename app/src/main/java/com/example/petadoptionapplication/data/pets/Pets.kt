package com.example.petadoptionapplication.data.pets

data class Pets(
    var id: Int = 0,
    var name: String = "",
    var url: String = "",
    var type: String = "",
    var age: Int = 0,
    var vaccinated: String = ""
)

data class PetList(
    var pets: List<Pets>
)