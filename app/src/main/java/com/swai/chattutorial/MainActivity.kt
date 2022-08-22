package com.swai.chattutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.stringResource
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(
                backgroundSyncEnabled = true,
                userPresence = true,
                persistenceEnabled = true,
                uploadAttachmentsNetworkType = UploadAttachmentsNetworkType.NOT_ROAMING,
            ),
            appContext = applicationContext
        )

        val client = ChatClient.Builder("b67pax5b2wdq", applicationContext)
            .withPlugin(offlinePluginFactory)
            .logLevel(ChatLogLevel.ALL)
            .build()

        val user = User(
            id = "tutorial-droid",
            name = "Tutorial Droid",
            image = "https://bit.ly/2TIt8NR"
        )
        client.connectUser(
            user = user,
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoidHV0b3JpYWwtZHJvaWQifQ.NhEr0hP9W9nwqV7ZkdShxvi02C5PR7SJE7Cs4y7kyqg"
        ).enqueue()
        setContent {
            ChatTheme {
                ChannelsScreen(
                    title = stringResource(id = R.string.app_name),
                    isShowingSearch = true,
                    onItemClick = { channel ->
                        startActivity(
                            MessagesActivity.getIntent(
                                this,
                                channel.cid
                            )
                        )
                    },
                    onBackPressed = { finish() }
                )
            }
        }
    }
}

