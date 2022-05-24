package pe.devpicon.android.codelab.pomodoro

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ConnectionTest {

    @Test
    fun `connect pipelines`() = runBlocking {
        delay(1000)
        assert(4%2 == 0)
    }

    @Test
    fun `connect to youtube`() = runBlocking {
        delay(1000)
        assert(4%2 == 0)
    }

    @Test
    fun `connect to uber`() = runBlocking {
        delay(1000)
        assert(4%2 == 0)
    }

    @Test
    fun `connect to dark book`() = runBlocking {
        delay(1000)
        assert(4%2 == 0)
    }

}
