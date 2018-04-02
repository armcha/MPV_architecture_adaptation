package io.github.armcha.architecturesampleproject.data.api

import io.github.armcha.architecturesampleproject.domain.model.User
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SomeApiService @Inject constructor(){

    fun getUsersList():Flowable<List<User>>{
        TODO()
    }
}