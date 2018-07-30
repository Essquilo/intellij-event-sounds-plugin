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

import com.intellij.openapi.diagnostic.Logger
import javazoom.jl.player.Player
import java.io.BufferedInputStream

private val log = Logger.getInstance(Sound::class.java)

class Sound(private val name: String) {

    var player: Player? = null
    fun play() {
        val filename = "$name.mp3"
        try {
            val buffer = BufferedInputStream(javaClass.getResourceAsStream(filename))
            player = Player(buffer)
            object : Thread() {
                override fun run() {
                    try {
                        player?.play()
                    } catch (e: Exception) {
                        log.error("Problem playing file $filename", e)
                    }

                }
            }.start()
        } catch (e: Exception) {
            log.error("Problem playing file $filename", e)
        }
        // run in new thread to play in background
    }

    fun stop() {
        player?.close()
    }
}