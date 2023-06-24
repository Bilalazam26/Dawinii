package com.grad.dawinii.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.grad.dawinii.datasource.local.DawiniiDao
import com.grad.dawinii.model.entities.User
import com.grad.dawinii.util.Constants
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.makeToast
import io.paperdb.Paper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthRepository(application: Application,val dao: DawiniiDao) {

    private var application: Application
    private var databaseReference: DatabaseReference
    private var authReference: FirebaseAuth
    var userMutableLiveData: MutableLiveData<User>
    var isLoggedOutMutableLiveData: MutableLiveData<Boolean>

    init {
        this.application = application
        this.databaseReference = FirebaseDatabase.getInstance().reference
        this.authReference = FirebaseAuth.getInstance()
        this.userMutableLiveData = MutableLiveData()
        this.isLoggedOutMutableLiveData = MutableLiveData()

        Paper.init(application)
    }

    suspend fun signUp(user: User) {
        //1. create user
        val email = user.email
        val password = user.password
        authReference.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    user.id = authReference.currentUser?.uid as String
                    if (authReference.currentUser?.isEmailVerified as Boolean) {
                        userMutableLiveData.postValue(user)
                        Prevalent.currentUser = user
                    } else {
                        makeToast(application, "Please Check Your Email for Verification")
                        authReference.currentUser?.sendEmailVerification()
                    }
                    CoroutineScope(Dispatchers.IO).launch { dao.insertUser(user) }
                    CoroutineScope(Dispatchers.IO).launch {
                        updateUser(user)
                    }
                    rememberUser(user.id)
                } else {
                    makeToast(application, "Failed! : ${it.exception?.message.toString()}")
                }
            }

    }

    suspend fun updateUser(user: User){
        databaseReference.child(Constants.USER_DATABASE_REFERENCE).child(user.id.toString()).setValue(user)
            .addOnFailureListener {
                makeToast(application, "Failed! : ${it.message.toString()}")
            }.addOnSuccessListener {
                makeToast(application, "Profile updated successfully")
            }
        Prevalent.currentUser = user
    }

    suspend fun logIn(email: String, password: String){
        if (!(email.isNullOrEmpty() || password.isNullOrEmpty())) {
            authReference.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it?.let {
                        val currentUser = authReference.currentUser as FirebaseUser
                        if(currentUser.isEmailVerified) {
                            CoroutineScope(Dispatchers.IO).launch {
                                getCurrentUser()
                            }
                            rememberUser(currentUser.uid)

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

    suspend fun getCurrentUser() {
        val uid = authReference.currentUser?.uid as String
        databaseReference.child("User").child(uid)
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = snapshot.getValue(User::class.java)
                    userMutableLiveData.postValue(user as User)
                    dao.insertUser(user)
                    Prevalent.currentUser = user
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(application, "access data base${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun logOut() {
        authReference.signOut()
        isLoggedOutMutableLiveData.postValue(true)
    }

    private fun rememberUser(uid: String) {
        Paper.book().write<String>(Constants.UID_PAPER_KEY, uid)
    }

    fun resetPassword(email: String) {
        authReference.sendPasswordResetEmail(email)
        makeToast(application, "Check your email to reset your password")
    }

}