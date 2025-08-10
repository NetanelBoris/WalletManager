package dev.netanel.wallet_manager.domain.usecases.appUser


import dev.netanel.wallet_manager.domain.models.AppUser
import dev.netanel.wallet_manager.domain.repositories.AppUserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class GetAppUserByMailAndPasswordUseCaseTest {

    private lateinit var repo: AppUserRepository
    private lateinit var useCase: GetAppUserByMailAndPasswordUseCase

    @Before
    fun setup() {
        repo = mockk(relaxed = true)
        useCase = GetAppUserByMailAndPasswordUseCase(repo)
    }

    @Test
    fun `returns user when repository finds a match`() = runTest {
        // Arrange
        val mail = "user@mail.com"
        val pass = "123456"
        val user = AppUser(
            mail = mail,
            hashedPassword = "hashed_123456",
            firstName = "Dana",
            lastName = "S",
            address = "Somewhere 1",
            phoneNumber = "050-0000000"
        )
        coEvery { repo.getAppUserByMailAndPassword(mail, pass) } returns user

        // Act
        val result = useCase(mail, pass)

        // Assert
        assertEquals(user, result)
        coVerify(exactly = 1) { repo.getAppUserByMailAndPassword(mail, pass) }
    }

    @Test
    fun `returns null when repository has no match`() = runTest {
        // Arrange
        val mail = "nope@mail.com"
        val pass = "wrong"
        coEvery { repo.getAppUserByMailAndPassword(mail, pass) } returns null

        // Act
        val result = useCase(mail, pass)

        // Assert
        assertNull(result)
        coVerify(exactly = 1) { repo.getAppUserByMailAndPassword(mail, pass) }
    }
}
