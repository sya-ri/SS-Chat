package me.syari.ss.chat

import me.syari.ss.chat.Main.Companion.chatPlugin
import me.syari.ss.core.Main.Companion.console
import me.syari.ss.core.auto.OnEnable
import me.syari.ss.core.config.CreateConfig.config
import me.syari.ss.core.config.dataType.ConfigDataType
import org.bukkit.command.CommandSender

object ConfigLoader: OnEnable {
    override fun onEnable() {
        loadConfig(console)
    }

    var jpChatFormat = "%sender%: %jp% (%message%)"
        private set

    var chatFormat = "%sender%: %message%"
        private set

    var discordHookChannel = 0L
        private set

    var discordSendFormat = "**%sender%**: %message%"
        private set

    var discordSendJpFormat = "**%sender%**: %jp% (%message%)"
        private set

    var discordReceiveFormat = "&5[Discord] &f%sender%: %message%"
        private set

    private val defaultConfig = mapOf(
        "format.jp" to jpChatFormat,
        "format.normal" to chatFormat,
        "discord.channel" to discordHookChannel,
        "discord.format.send" to discordSendFormat,
        "discord.format.receive" to discordReceiveFormat
    )

    fun loadConfig(output: CommandSender) {
        config(chatPlugin, output, "config.yml", default = defaultConfig) {
            jpChatFormat = get("format.jp", ConfigDataType.STRING, jpChatFormat, false)
            chatFormat = get("format.normal", ConfigDataType.STRING, chatFormat, false)
            discordHookChannel = get("discord.channel", ConfigDataType.LONG, discordHookChannel, false)
            discordSendFormat = get("discord.format.send", ConfigDataType.STRING, discordSendFormat, false)
            discordSendJpFormat = get("discord.format.sendjp", ConfigDataType.STRING, discordSendJpFormat, false)
            discordReceiveFormat = get("discord.format.receive", ConfigDataType.STRING, discordReceiveFormat, false)
        }
    }
}