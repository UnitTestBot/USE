package org.utbot.rd.loggers

import com.jetbrains.rd.util.*
import com.jetbrains.rd.util.lifetime.Lifetime
import mu.KLogger
import org.utbot.common.LoggerWithLogMethod
import org.utbot.rd.generated.LoggerModel

fun Logger.withLevel(logLevel: LogLevel): LoggerWithLogMethod = LoggerWithLogMethod {
    this.log(logLevel, it)
}

fun Logger.info(): LoggerWithLogMethod = LoggerWithLogMethod {
    this.info(it)
}
fun Logger.debug(): LoggerWithLogMethod = LoggerWithLogMethod {
    this.debug(it)
}
fun Logger.trace(): LoggerWithLogMethod = LoggerWithLogMethod {
    this.trace(it)
}

fun overrideDefaultRdLoggerFactoryWithKLogger(logger: KLogger) {
    if (Statics<ILoggerFactory>().get() !is UtRdKLoggerFactory) {
        Logger.set(Lifetime.Eternal, UtRdKLoggerFactory(logger))
    }
}

fun LoggerModel.setup(logger: KLogger, processLifetime: Lifetime) {
    // we do not log trace on error here because it's a job of clint-side process logger
    // server-side process logger can only log trace of where it was set up from which isn't particularly useful
    val rdLogger = UtRdKLogger(logger, "", logTraceOnError = false)
    // currently we do not specify log level for different categories
    // though it is possible with some additional map on categories -> consider performance
    // this logLevel is obtained from KotlinLogger
    getCategoryMinimalLogLevel.set(rdLogger.logLevel.ordinal)

    log.advise(processLifetime) {
        val logLevel = UtRdRemoteLogger.logLevelValues[it.logLevelOrdinal]
        // assume throwable already in message
        rdLogger.log(logLevel, it.message, null)
    }

    initRemoteLogging.fire(Unit)
}
