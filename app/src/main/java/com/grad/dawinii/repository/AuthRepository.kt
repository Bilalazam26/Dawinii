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
import io.paperdb.Paper

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

        Paper.init(application)
    }

    fun signUp(user: User) {
        //1. create user
        val email = user.email
        val password = user.password
        authReference.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val updatedUser = user
                    updatedUser.id = authReference.currentUser?.uid as String
                    if(authReference.currentUser?.isEmailVerified as Boolean) {
                        userMutableLiveData.postValue(authReference.currentUser)
                    } else {
                        makeToast(application, "Please Check Your Email for Verification")
                        authReference.currentUser?.sendEmailVerification()
                    }
                    saveUser(updatedUser)
                    rememberUser(email, password)
                } else {
                    makeToast(application, "Failed! : ${it.exception?.message.toString()}")
                }
            }

    }

    private fun saveUser(user: User){
        databaseReference.child(Constants.USER_DATABASE_REFERENCE).child(user.id).setValue(user)
            .addOnFailureListener {
                makeToast(application, "Failed! : ${it.message.toString()}")
            }

    }

    fun logIn(email: String, password: String){
        if (!(email.isNullOrEmpty() || password.isNullOrEmpty())) {
            authReference.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it?.let {
                        val currentUser = authReference.currentUser as FirebaseUser
                        if(currentUser.isEmailVerified) {
                            userMutableLiveData.postValue(authReference.currentUser)
                            rememberUser(email, password)

                        } else {
                            currentUser.sendEmailVerification()
                            makeToast(application, "Please Check Your Email for Verification")
                        }

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

    private fun rememberUser(email: String, password: String) {
        Paper.book().write<String>(Constants.EMAIL_PAPER_KEY, email)
        Paper.book().write<String>(Constants.PASSWORD_PAPER_KEY, password)
    }

    fun resetPassword(email: String) {
        authReference.sendPasswordResetEmail(email)
        makeToast(application, "Check your email to reset your password")
    }
}