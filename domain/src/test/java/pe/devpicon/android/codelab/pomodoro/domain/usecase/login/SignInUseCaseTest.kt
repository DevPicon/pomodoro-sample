package pe.devpicon.android.codelab.pomodoro.domain.usecase.login

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import pe.devpicon.android.codelab.pomodoro.domain.common.ErrorHandler
import pe.devpicon.android.codelab.pomodoro.domain.common.FakeErrorHandler
import pe.devpicon.android.codelab.pomodoro.domain.common.ResultWrapper
import pe.devpicon.android.codelab.pomodoro.domain.model.User
import pe.devpicon.android.codelab.pomodoro.domain.repository.FakeLoginRepository
import pe.devpicon.android.codelab.pomodoro.domain.repository.LoginRepository

class SignInUseCaseTest {

    private lateinit var useCase: SignInUseCase

    @Before
    fun setUp() {
        val loginRepository: LoginRepository = FakeLoginRepository()
        val errorHandler: ErrorHandler = FakeErrorHandler()
        useCase = SignInUseCase(loginRepository, errorHandler)
    }

    @Test
    fun `Given non empty email and password When the use case is executed Then a User is returned `() =
        runBlocking {
            val email = "test@test.com"
            val password = "jhbl1k3b24œ|@#¢"
            val result: ResultWrapper = useCase(
                SignInUseCase.SignInParams(
                    email,
                    password
                )
            )

            assertTrue(result is ResultWrapper.Success<*>)
            assertEquals(email, (result as ResultWrapper.Success<User>).value.email)
        }

    @Test
    fun `Given invalid email or password When the use case is executed Then it returns a Failure`() =
        runBlocking {
            val email = ""
            val password = "jhbl1k3b24œ|@#¢"
            val result: ResultWrapper = useCase(
                SignInUseCase.SignInParams(
                    email,
                    password
                )
            )

            assertTrue(result is ResultWrapper.Failure)
        }

    @After
    fun tearDown() {
    }
}
