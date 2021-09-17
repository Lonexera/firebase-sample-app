package com.example.firebasesampleapp

interface FirestoreListener {

    fun addToFirestore(text: String)
    fun getFromFirestore(listener: LoadingListener)
}