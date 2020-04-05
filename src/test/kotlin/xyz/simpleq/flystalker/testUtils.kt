package xyz.simpleq.flystalker

import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.fail
import java.util.concurrent.TimeUnit

fun MockWebServer.takeRequestOrFail(
    timeout: Long = 500,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS
): RecordedRequest =
    takeRequest(timeout, timeUnit)
        ?: fail { "Request not recorded" }
