/*
 * MIT License
 *
 * Copyright (c) 2018 Ivan Prymak
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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
