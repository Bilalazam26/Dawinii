package com.grad.dawinii.view.main.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.grad.dawinii.R
import com.grad.dawinii.model.entities.User
import com.grad.dawinii.theme.PrimaryColor
import com.grad.dawinii.theme.SecondaryColor
import com.grad.dawinii.view.main.history.components.TextImageCard
import com.grad.dawinii.view.main.history.components.UserCard

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    user: User?,
    doctorNoteOnClick: () -> Unit,
    analysisOnClick: () -> Unit,
    medicinesOnClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(unbounded = true)
                .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(PrimaryColor, SecondaryColor),
                        end = Offset(0f, 0f),  // Top
                        start = Offset(0f, Float.POSITIVE_INFINITY) // Bottom
                    )
                )
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.History),
                    color = Color.White,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.your_previous_medicines_analysis_and_doctor_notes),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light
                )

                UserCard(
                    user = user
                )

            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextImageCard(
                icon = painterResource(R.drawable.doctor),
                text = stringResource(R.string.doctor_s_note),
                onClick = { doctorNoteOnClick() },
                modifier = Modifier.fillMaxHeight(.333f)
            )
            TextImageCard(
                icon = painterResource(R.drawable.analysis),
                text = stringResource(R.string.analysis),
                onClick = { analysisOnClick() },
                modifier = Modifier.fillMaxHeight(.5f)
            )
            TextImageCard(
                icon = painterResource(R.drawable.medicine),
                text = stringResource(R.string.medicines),
                onClick = { medicinesOnClick() },
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

