package me.syari.ss.chat

import me.syari.ss.chat.ConfigLoader.chatFormat
import me.syari.ss.chat.ConfigLoader.jpChatFormat
import me.syari.ss.chat.Main.Companion.enableDiscord
import me.syari.ss.chat.converter.IMEConverter
import me.syari.ss.core.auto.Event
import me.syari.ss.core.code.StringEditor.toColor
import me.syari.ss.core.code.StringEditor.toUncolor
import me.syari.ss.core.message.Message.broadcast
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerChatEvent

object ChatEventListener: Event {
    @EventHandler
    fun on(e: AsyncPlayerChatEvent) {
        val player = e.player
        val message = e.message
        e.isCancelled = true
        send(player, message.toUncolor)
    }

    private fun send(sender: Player, message: String) {
        if(matchHalfWidthChar(message)){
            sendJapanese(sender, message)
        } else {
            sendNormal(sender, message)
        }
    }

    private fun sendNormal(sender: Player, message: String) {
        if(enableDiscord) DiscordHook.send(sender, message)
        broadcast(chatFormat.replaceWith(sender, message).toColor)
    }

    private fun sendJapanese(sender: Player, message: String) {
        val jpMessage = IMEConverter.convertWithIMEFromRoma(message)
        if(jpMessage == message){
            sendNormal(sender, message)
        } else {
            if(enableDiscord) DiscordHook.send(sender, message, jpMessage)
            broadcast(jpChatFormat.replaceWith(sender, message).replaceJp(jpMessage).toColor)
        }
    }

    internal fun String.replaceWith(sender: Player, message: String): String {
        return replace("%sender%", sender.displayName).replace("%message%", message)
    }

    internal fun String.replaceJp(jpMessage: String): String {
        return replace("%jp%", jpMessage)
    }

    private fun matchHalfWidthChar(text: String): Boolean {
        return text.matches("^[a-zA-Z0-9!-/:-@\\[-`{-~]*\$".toRegex())
    }
}