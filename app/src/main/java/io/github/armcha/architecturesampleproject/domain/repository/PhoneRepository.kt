package io.github.armcha.architecturesampleproject.domain.repository

import io.github.armcha.architecturesampleproject.domain.model.Phone
import kotlinx.coroutines.Deferred

interface PhoneRepository {

    fun getDummyPhones(): Deferred<List<Phone>>

    suspend fun saveDummyData()

    suspend fun doSomeHeavyWork(): String
}