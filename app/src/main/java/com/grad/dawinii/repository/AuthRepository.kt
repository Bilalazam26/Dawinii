package com.grad.dawinii.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.grad.dawinii.model.User
import com.grad.dawinii.util.Constants
import com.grad.dawinii.util.makeToast

class AuthRepository(application: Application) {

    private var application: Application
    private var databaseReference: DatabaseReference
    private var authReference: FirebaseAuth
    var userMutableLiveData: MutableLiveData<FirebaseUser>
    var isLoggedOutMutableLiveData: MutableLiveData<Boolean>

    init {
        this.application = application
        this.databaseReference = FirebaseDatabase.getInstance().reference
        this.authReference = FirebaseAuth.getInstance()
        this.userMutableLiveData = MutableLiveData()
        this.isLoggedOutMutableLiveData = MutableLiveData()
    }

    fun signUp(user: User) {
        //1. create user
        authReference.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val updatedUser = user
                    updatedUser.id = authReference.currentUser?.uid as String
                    userMutableLiveData.postValue(authReference.currentUser)
                    saveUser(updatedUser)
                } else {
                    makeToast(application, "Failed! : ${it.exception?.message.toString()}")
                }
            }

    }

    fun saveUser(user: User){
        databaseReference.child(Constants.USER_DATABASE_REFERENCE).child(user.id).setValue(user)
            .addOnSuccessListener {
                makeToast(application, "Signed Up SuccessFully")
            }.addOnFailureListener {
                makeToast(application, "Failed! : ${it.message.toString()}")
            }

    }

    fun logIn(email: String, password: String){
        if (!(email.isNullOrEmpty() || password.isNullOrEmpty())) {
            authReference.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it?.let {
                        userMutableLiveData.postValue(authReference.currentUser)
                        makeToast(application, "Logged In Successfully")
                    }
                }.addOnFailureListener {
                    makeToast(application, "Failed to Log In : ${it.message.toString()}")
                }
        }

    }

    fun logOut() {
        authReference.signOut()
        isLoggedOutMutableLiveData.postValue(true)
    }
}