package lifestyle.dt.common

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.AutoScan
import io.kotest.core.spec.IsolationMode
import io.kotest.core.test.AssertionMode
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.mockk.clearAllMocks

@AutoScan
object TestConfig : AbstractProjectConfig(), TestListener {

    // AbstractProjectConfig
    override val parallelism = 1
    override val assertionMode = AssertionMode.None
    override val globalAssertSoftly = true
    override val failOnIgnoredTests = false
    override val isolationMode = IsolationMode.SingleInstance

    override fun extensions() = listOf(SpringExtension)

    // TestListener
    override suspend fun afterEach(testCase: TestCase, result: TestResult) = clearAllMocks()
}