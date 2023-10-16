package org.utbot.framework

import mu.KLogger
import org.slf4j.Logger

class UtLogger : KLogger {
    override fun debug(msg: () -> Any?) {}

    override fun debug(t: Throwable?, msg: () -> Any?) {}

    override fun debug(marker: mu.Marker?, msg: () -> Any?) {}

    override fun debug(marker: mu.Marker?, t: Throwable?, msg: () -> Any?) {}

    override fun debug(p0: String?) {}

    override fun debug(p0: String?, p1: Any?) {}

    override fun debug(p0: String?, p1: Any?, p2: Any?) {}

    override fun debug(p0: String?, vararg p1: Any?) {}

    override fun debug(p0: String?, p1: Throwable?) {}

    override fun debug(p0: org.slf4j.Marker?, p1: String?) {}

    override fun debug(p0: org.slf4j.Marker?, p1: String?, p2: Any?) {}

    override fun debug(p0: org.slf4j.Marker?, p1: String?, p2: Any?, p3: Any?) {}

    override fun debug(p0: org.slf4j.Marker?, p1: String?, vararg p2: Any?) {}

    override fun debug(p0: org.slf4j.Marker?, p1: String?, p2: Throwable?) {}

    override fun info(t: Throwable?, msg: () -> Any?) {}

    override fun info(msg: () -> Any?) {}

    override fun info(marker: mu.Marker?, msg: () -> Any?) {}

    override fun info(marker: mu.Marker?, t: Throwable?, msg: () -> Any?) {}

    override fun info(p0: String?) {}

    override fun info(p0: String?, p1: Any?) {}

    override fun info(p0: String?, p1: Any?, p2: Any?) {}

    override fun info(p0: String?, vararg p1: Any?) {}

    override fun info(p0: String?, p1: Throwable?) {}

    override fun info(p0: org.slf4j.Marker?, p1: String?) {}

    override fun info(p0: org.slf4j.Marker?, p1: String?, p2: Any?) {}

    override fun info(p0: org.slf4j.Marker?, p1: String?, p2: Any?, p3: Any?) {}

    override fun info(p0: org.slf4j.Marker?, p1: String?, vararg p2: Any?) {}

    override fun info(p0: org.slf4j.Marker?, p1: String?, p2: Throwable?) {}

    override val underlyingLogger: Logger
        get() = TODO("Not yet implemented")

    override fun <T : Throwable> catching(throwable: T) {}

    override fun entry(vararg argArray: Any?) {}

    override fun error(msg: () -> Any?) {}

    override fun error(t: Throwable?, msg: () -> Any?) {}

    override fun error(marker: mu.Marker?, msg: () -> Any?) {}

    override fun error(marker: mu.Marker?, t: Throwable?, msg: () -> Any?) {}

    override fun error(p0: String?) {}

    override fun error(p0: String?, p1: Any?) {}

    override fun error(p0: String?, p1: Any?, p2: Any?) {}

    override fun error(p0: String?, vararg p1: Any?) {}

    override fun error(p0: String?, p1: Throwable?) {}

    override fun error(p0: org.slf4j.Marker?, p1: String?) {}

    override fun error(p0: org.slf4j.Marker?, p1: String?, p2: Any?) {}

    override fun error(p0: org.slf4j.Marker?, p1: String?, p2: Any?, p3: Any?) {}

    override fun error(p0: org.slf4j.Marker?, p1: String?, vararg p2: Any?) {}

    override fun error(p0: org.slf4j.Marker?, p1: String?, p2: Throwable?) {}

    override fun exit() {}

    override fun <T> exit(result: T): T {
        TODO("'exit' is yet not implemented")
    }

    override fun getName() = ""

    override fun isDebugEnabled() = false

    override fun isDebugEnabled(p0: org.slf4j.Marker?) = false

    override fun isErrorEnabled() = false

    override fun isErrorEnabled(p0: org.slf4j.Marker?) = false

    override fun isInfoEnabled() = false

    override fun isInfoEnabled(p0: org.slf4j.Marker?) = false

    override fun isTraceEnabled() = false

    override fun isTraceEnabled(p0: org.slf4j.Marker?) = false

    override fun isWarnEnabled() = false

    override fun isWarnEnabled(p0: org.slf4j.Marker?) = false

    override fun <T : Throwable> throwing(throwable: T): T {
        TODO("'throwing' is yet not implemented")
    }

    override fun trace(msg: () -> Any?) {}

    override fun trace(t: Throwable?, msg: () -> Any?) {}

    override fun trace(marker: mu.Marker?, msg: () -> Any?) {}

    override fun trace(marker: mu.Marker?, t: Throwable?, msg: () -> Any?) {}

    override fun trace(p0: String?) {}

    override fun trace(p0: String?, p1: Any?) {}

    override fun trace(p0: String?, p1: Any?, p2: Any?) {}

    override fun trace(p0: String?, vararg p1: Any?) {}

    override fun trace(p0: String?, p1: Throwable?) {}

    override fun trace(p0: org.slf4j.Marker?, p1: String?) {}

    override fun trace(p0: org.slf4j.Marker?, p1: String?, p2: Any?) {}

    override fun trace(p0: org.slf4j.Marker?, p1: String?, p2: Any?, p3: Any?) {}

    override fun trace(p0: org.slf4j.Marker?, p1: String?, vararg p2: Any?) {}

    override fun trace(p0: org.slf4j.Marker?, p1: String?, p2: Throwable?) {}

    override fun warn(msg: () -> Any?) {}

    override fun warn(t: Throwable?, msg: () -> Any?) {}

    override fun warn(marker: mu.Marker?, msg: () -> Any?) {}

    override fun warn(marker: mu.Marker?, t: Throwable?, msg: () -> Any?) {}

    override fun warn(p0: String?) {}

    override fun warn(p0: String?, p1: Any?) {}

    override fun warn(p0: String?, vararg p1: Any?) {}

    override fun warn(p0: String?, p1: Any?, p2: Any?) {}

    override fun warn(p0: String?, p1: Throwable?) {}

    override fun warn(p0: org.slf4j.Marker?, p1: String?) {}

    override fun warn(p0: org.slf4j.Marker?, p1: String?, p2: Any?) {}

    override fun warn(p0: org.slf4j.Marker?, p1: String?, p2: Any?, p3: Any?) {}

    override fun warn(p0: org.slf4j.Marker?, p1: String?, vararg p2: Any?) {}

    override fun warn(p0: org.slf4j.Marker?, p1: String?, p2: Throwable?) {}
}