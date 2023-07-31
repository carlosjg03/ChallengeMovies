package com.example.movieschallenge.save

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException


class SaveData(
    private val path: File
):SaveDataContract {
    override fun saveData(nameFile:String, data:String){
        try {
            val newFile = File(path,nameFile)
            if (!newFile.exists()) {
                newFile.createNewFile()
            }
            val fw = FileWriter(newFile.absoluteFile)
            val bw = BufferedWriter(fw)
            bw.write(data)
            bw.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun getData(nameFile:String):String{
        val newFile = File(path,nameFile)
        val br = BufferedReader(FileReader(newFile))
        var returnStr = ""
        var st: String?
        while (br.readLine().also { st = it } != null) // Print the string
            returnStr+=st
        return returnStr
    }
}