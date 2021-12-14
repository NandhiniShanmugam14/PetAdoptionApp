package com.example.petadoptionapplication.data.others

import com.example.petadoptionapplication.data.LoginHistory.loginTimestamp

data class others(
    var email: String = "",
    var reservationsAt: String = "",
    var textView: String = ""
)

data class usersList(
    var users: List<others>
)
