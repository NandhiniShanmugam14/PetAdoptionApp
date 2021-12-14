package com.example.petadoptionapplication.data.LoginHistory


data class loginTimestamp(
    var loginTimestamp: Long = 0
)

data class loginEntriesList(
    var loginEntries: List<loginTimestamp>
)
