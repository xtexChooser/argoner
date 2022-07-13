package argoner.server.main

import argoner.server.ArgonerServer

fun main(args: Array<String>) {
    ArgonerServer.logger.info("Bootstrapping Argoner Server...")
    ArgonerServer.start()
}