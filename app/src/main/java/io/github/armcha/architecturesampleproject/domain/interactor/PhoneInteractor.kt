package io.github.armcha.architecturesampleproject.domain.interactor

import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.model.Phone
import io.github.armcha.architecturesampleproject.domain.repository.PhoneRepository
import kotlinx.coroutines.experimental.Deferred
import javax.inject.Inject

@PerScreen
class PhoneInteractor @Inject constructor(private val phoneRepository: PhoneRepository) {

    fun getPhones(): Deferred<List<Phone>> = phoneRepository.getDummyPhones()

    suspend fun saveDummyData() {
        phoneRepository.saveDummyData()
    }

    suspend fun doSomeHeavyWorkWithResult() = phoneRepository.doSomeHeavyWork()

    fun doSomeHeavyWorkWithResultNew(): suspend () -> String = { phoneRepository.doSomeHeavyWork() }
}