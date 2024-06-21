package com.example.bioserenity_websocket_test.connection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bioserenity_websocket_test.utils.Constant
/**The ConnectionView class provides composable functions for displaying connection-related controls in a UI.
 * The TopBarView function creates a top bar with a switch to toggle automatic connection mode,
 * updating the state and initiating auto-connection if enabled. The BottomBarView function conditionally displays
 * a button to connect or disconnect based on the connection state and auto-connection mode. These functions ensure
 * an interactive and responsive UI for managing WebSocket connections.*/
class ConnectionView {
    @Composable
    fun TopBarView(managerConnection: ManagerConnection) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically


        ) {

            Switch(
                modifier = Modifier.testTag(Constant.switchTag),
                checked = managerConnection.isAuto.value,
                onCheckedChange = {
                    managerConnection.isAuto.value = !managerConnection.isAuto.value
                    if (managerConnection.isAuto.value) {
                        managerConnection.connect()
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                modifier = Modifier.testTag(Constant.switchTagStatus),
                text = if (managerConnection.isAuto.value) Constant.autoConnect else Constant.manualConnect,

                )
        }
    }

    @Composable
    fun BottomBarView(managerConnection: ManagerConnection) {
        if (!managerConnection.isAuto.value) {
            Button(
                modifier = Modifier
                    .testTag(Constant.connectToWss)
                    .fillMaxWidth()
                    .padding(8.dp),

                onClick = {
                    if (!managerConnection.isConnect.value) {
                        managerConnection.connect()
                    } else {
                        managerConnection.closeConnection()
                    }
                },

                ) {
                Text(
                    modifier = Modifier.testTag(Constant.status),
                    text = if (!managerConnection.isConnect.value) Constant.connectToWss else
                        Constant.closeConnect,
                    fontSize = 20.sp

                )
            }
        } else {
            Text(
                modifier = Modifier

                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                text = if (managerConnection.isConnect.value) Constant.connect else
                    Constant.closed
            )
        }
    }
}