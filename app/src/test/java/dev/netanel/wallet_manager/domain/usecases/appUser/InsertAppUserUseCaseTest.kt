package dev.netanel.wallet_manager.domain.usecases.appUser

import dev.netanel.wallet_manager.domain.models.AppUser
import dev.netanel.wallet_manager.domain.repositories.AppUserRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InsertAppUserUseCaseTest {

    private lateinit var repo: AppUserRepository
    private lateinit var useCase: InsertAppUserUseCase

    @Before
    fun setup() {
        repo = mockk(relaxed = true)
        useCase = InsertAppUserUseCase(repo)
    }

    @Test
    fun `inserts user into repository`() = runTest {
        // Arrange
        val user = AppUser(
            mail = "test@mail.com",
            hashedPassword = "hashed123",
            firstName = "John",
            lastName = "Doe",
            address = "123 Street",
            phoneNumber = "050-0000000"
        )

        // Act
        useCase(user)

        // Assert
        coVerify(exactly = 1) { repo.insertAppUser(user) }
    }
}
