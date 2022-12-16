package com.grad.dawinii.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.grad.dawinii.model.User
import com.grad.dawinii.repository.AuthRepository
import com.grad.dawinii.util.makeToast

class AuthViewModel(application: Application): AndroidViewModel(application){

    private var authRepository: AuthRepository
    var userMutableLiveData: MutableLiveData<FirebaseUser>
    var isLoggedOutMutableLiveData: MutableLiveData<Boolean>

    init {
        authRepository = AuthRepository(application)
        userMutableLiveData = authRepository.userMutableLiveData
        isLoggedOutMutableLiveData = authRepository.isLoggedOutMutableLiveData
    }

    fun signUp(user: User) {
        authRepository.signUp(user)
    }

    fun logIn(email:String, password:String) {
        authRepository.logIn(email, password)
    }

    fun logOut() {
        authRepository.logOut()
    }

}