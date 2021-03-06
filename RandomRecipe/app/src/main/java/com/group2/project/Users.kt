package com.group2.project

data class Users(val Name: String, val Age: String, val Diet: String, val Subscription: String) {
    companion object {
        fun from(map: HashMap<String, String>) = object {
            val Name by map
            val Age by map
            val Diet by map
            val Subscription by map

            val data = Users(Name, Age, Diet, Subscription)
        }.data
    }
}