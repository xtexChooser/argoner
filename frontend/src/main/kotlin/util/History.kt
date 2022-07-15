package argoner.frontend.web.util

import kotlinx.browser.window
import org.w3c.dom.History

val history get() = window.history

fun History.backOrHome() {
    back()
    window.setTimeout({
        window.open("./#/", "_self")
    }, 5)
}