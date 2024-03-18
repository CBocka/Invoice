package com.hamilton.entity.customers

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer(var name : String, var email:String, var phone:String, var city:String, var address:String):Comparable<Customer> {
    @PrimaryKey var id:String = ""

    private fun generaString(nextId : Int): String {
        val frase:String = String.format("%03d", nextId) + "-"  + name.substring(0,3).toUpperCase()
        return frase
    }

    fun assignID(lastID:Int){
        id = generaString(lastID)
    }

    companion object{
        const val CUSTOMER_KEY = "CUSTOMER"
    }

    override fun compareTo(other: Customer): Int {
        return this.id.compareTo(other.id)
    }
}