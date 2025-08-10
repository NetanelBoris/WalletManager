package dev.netanel.wallet_manager.domain.usecases.appUser


import dev.netanel.wallet_manager.domain.repositories.AppUserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UserExistsUseCaseTest {

    private lateinit var repo: AppUserRepository
    private lateinit var useCase: UserExistsUseCase

    @Before
    fun setup() {
        repo = mockk(relaxed = true)
        useCase = UserExistsUseCase(repo)
    }

    @Test
    fun `returns true when repository finds user`() = runTest {
        // Arrange
        val email = "user@mail.com"
        coEvery { repo.userExists(email) } returns true

        // Act
        val result = useCase(email)

        // Assert
        assertTrue(result)
        coVerify(exactly = 1) { repo.userExists(email) }
    }

    @Test
    fun `returns false when repository does not find user`() = runTest {
        // Arrange
        val email = "notfound@mail.com"
        coEvery { repo.userExists(email) } returns false

        // Act
        val result = useCase(email)

        // Assert
        assertFalse(result)
        coVerify(exactly = 1) { repo.userExists(email) }
    }
}
