package com.example.bioserenity_websocket_test.view.Car

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.bioserenity_websocket_test.data.model.Car
import com.example.bioserenity_websocket_test.data.repository.ManagerCarInfo
import com.example.bioserenity_websocket_test.data.utils.Constant
import com.example.bioserenity_websocket_test.data.utils.TestLog
import kotlin.math.abs
import kotlin.math.round

/**
 * The CarView class defines a UI for displaying a list of cars and their details using Jetpack Compose.
 * It contains a CarList function that displays the cars in a LazyColumn, and an ItemCard function that
 * creates individual cards for each car, updating the selected car's speed. It also includes a RowItem
 * function for displaying car details in a row format and an AnimatedLinearProgressIndicator function for
 * showing the speed progress with an animation.*/
class CarView {
    var progress: MutableState<Float> = mutableStateOf(0.0f)

    @Composable
    fun CarList(managerCar: ManagerCarInfo) {
        val indexClicked = remember { mutableStateOf(-1) }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(managerCar.carsT.value.toList()) { car ->
                ItemCard(
                    item = car,
                    callBack = {
                        managerCar.getCurrentSpeed(
                            car.name,
                            indexClicked.value == car.cv
                        )
                    },
                    indexClicked = indexClicked
                )
            }
        }
    }


    @Composable
    fun ItemCard(item: Car, callBack: () -> Unit, indexClicked: MutableState<Int>) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(Constant.ItemCar + item.cv)
                .padding(8.dp)
                .clickable {
                    callBack()
                    if (indexClicked.value != item.cv) {
                        indexClicked.value = item.cv
                    } else {
                        indexClicked.value = -1
                    }
                    TestLog.i(
                        tag = "ItemCard",
                        message = "new cv: ${indexClicked.value}, test: ${item.cv != indexClicked.value}",
                        false
                    )
                }
                .focusable(item.cv == indexClicked.value),

            colors = CardDefaults.cardColors(
                containerColor =
                if (item.cv == indexClicked.value || item.name.equals(Constant.testCar))
                    MaterialTheme.colorScheme.errorContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(IntrinsicSize.Max)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround

            ) {
                RowItem(
                    title = "Brand: ", value = item.brand,
                    title1 = "Name:", value1 = item.name
                )
                RowItem(
                    title = "Max Speed: ", value = "${round(item.speedMax)} KM/H",
                    title1 = "Speed:", value1 = "${round(item.currentSpeed)} KM/H", isFirst = false
                )
                if (item.cv == indexClicked.value || item.name == Constant.testCar) {
                    progress.value = abs((item.currentSpeed / item.speedMax)).toFloat()

                    AnimatedLinearProgressIndicator()
                }
            }
        }
    }

    @Composable
    fun RowItem(
        title: String,
        value: String,
        title1: String,
        value1: String,
        isFirst: Boolean = true
    ) {
        val annotatedString = buildAnnotatedString {
            append(title)
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(value)
            pop()
        }

        val annotatedString1 = buildAnnotatedString {
            append(title1)
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(value1)
            pop()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .width(IntrinsicSize.Max)
                .padding(16.dp),
            //verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(
                text = annotatedString,
                style = MaterialTheme.typography.bodyLarge, overflow = TextOverflow.Ellipsis,
                maxLines = 1

            )
            if (isFirst)
                Text(
                    text = annotatedString1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge
                )
        }
        if (!isFirst)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(IntrinsicSize.Max)
                    .padding(16.dp),
                //verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Text(
                    text = annotatedString1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
    }

    @Composable
    fun AnimatedLinearProgressIndicator() {
        val animatedProgress by animateFloatAsState(
            targetValue = progress.value,
            animationSpec = tween(
                durationMillis = 1000
            )
        )
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(Constant.animationTag)
        )
    }

}