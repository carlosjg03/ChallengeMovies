package com.example.movieschallenge.save

interface SaveDataContract {
    fun saveData(nameFile: String, data: String)
    fun getData(nameFile: String):String
}