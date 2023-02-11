package com.grad.dawinii.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.grad.dawinii.datasource.local.DawiniiDao
import com.grad.dawinii.datasource.local.LocalDatabase
import com.grad.dawinii.model.entities.User
import com.grad.dawinii.repository.AuthRepository
import com.grad.dawinii.util.makeToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(application: Application): AndroidViewModel(application){

    private var authRepository: AuthRepository
    var userMutableLiveData: MutableLiveData<User>
    var isLoggedOutMutableLiveData: MutableLiveData<Boolean>

    init {
        val dao = LocalDatabase.localDbInstance(application)?.dawinniDao() as DawiniiDao
        authRepository = AuthRepository(application, dao)
        userMutableLiveData = authRepository.userMutableLiveData
        isLoggedOutMutableLiveData = authRepository.isLoggedOutMutableLiveData
    }

    fun signUp(user: User) {
        viewModelScope.launch { authRepository.signUp(user) }
    }

    fun logIn(email:String, password:String) {
        viewModelScope.launch { authRepository.logIn(email, password) }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.updateUser(user)
        }
    }

    fun logOut() {
        authRepository.logOut()
    }

    fun resetPassword(email: String) {
        authRepository.resetPassword(email)
    }

}