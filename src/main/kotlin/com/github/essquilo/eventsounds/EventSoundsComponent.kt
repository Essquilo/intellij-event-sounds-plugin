package com.github.essquilo.eventsounds

import com.intellij.execution.ExecutionListener
import com.intellij.execution.ExecutionManager
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.util.messages.MessageBusConnection

class EventSoundsComponent(project: Project) : ProjectComponent {
    private val busConnection: MessageBusConnection = project.messageBus.connect()
    val success = Sound("success")
    val error = Sound("error")

    init {
        busConnection.subscribe(ExecutionManager.EXECUTION_TOPIC, object : ExecutionListener {
            override fun processStarted(executorId: String, env: ExecutionEnvironment, handler: ProcessHandler) {
                super.processStarted(executorId, env, handler)
                stopAll()
                success.play()
            }

            override fun processNotStarted(executorId: String, env: ExecutionEnvironment) {
                super.processNotStarted(executorId, env)
                stopAll()
                error.play()
            }
        })
    }

    fun stopAll() {
        success.stop()
        error.stop()
    }

    override fun disposeComponent() {
        busConnection.disconnect()
    }

    override fun projectClosed() {
        stopAll()
    }

    override fun initComponent() {}

    override fun projectOpened() {}

    override fun getComponentName(): String = javaClass.simpleName
}
