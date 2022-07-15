package argoner.frontend.web.main

import argoner.frontend.web.ui.App
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        App()
    }
}