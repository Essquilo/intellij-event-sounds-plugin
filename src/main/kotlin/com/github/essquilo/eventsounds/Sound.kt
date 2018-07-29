package com.github.essquilo.eventsounds

import javazoom.jl.player.Player
import java.io.BufferedInputStream


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
                        println(e)
                    }

                }
            }.start()
        } catch (e: Exception) {
            println("Problem playing file $filename")
            println(e)
        }
        // run in new thread to play in background
    }

    fun stop() {
        player?.close()
    }
}