package argoner.client.endpoint

import argoner.client.ArgonerClient
import argoner.client.call
import argoner.common.endpoint.ServerInfo

suspend fun ArgonerClient.getServerInfo() = call<ServerInfo>("v1/info")
