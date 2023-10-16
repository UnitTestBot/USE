package org.utbot.framework

public object UtLogging {
    public final fun logger(func: () -> kotlin.Unit) : UtLogger = UtLogger()
    public final fun logger(name : String) : UtLogger = UtLogger()
}