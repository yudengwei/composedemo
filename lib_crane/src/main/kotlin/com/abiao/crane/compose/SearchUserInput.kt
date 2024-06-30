package com.abiao.crane.compose

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.abiao.crane.R
import com.abiao.crane.ui.captionTextStyle

const val MAX_PEOPLE = 4

@Composable
fun PeopleUserInput(
    titleSuffix: String = "",
    onPeopleChanged: (Int) -> Unit,
    peopleState: PeopleUserInputState = remember { PeopleUserInputState() }
) {
    Column {
        val transitionState = remember { peopleState.animationState }
        val tint = tintPeopleUserInput(transitionState = transitionState)
        val people = peopleState.people
        CraneUserInput(
            text = pluralStringResource(
                id = R.plurals.number_adults_selected,
                count = people,
                people,
                titleSuffix),
            vectorImageId = R.drawable.ic_person,
            tint = tint.value,
            onClick = {
                peopleState.addPerson()
                onPeopleChanged(peopleState.people)
            }
        )
        if (transitionState.targetState == PeopleUserInputAnimationState.Invalid) {
            Text(
                text = stringResource(id = R.string.error_max_people, MAX_PEOPLE),
                style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.secondary)
            )
        }
    }
}

@Composable
fun tintPeopleUserInput(
    transitionState: MutableTransitionState<PeopleUserInputAnimationState>
): State<Color> {
    val validColor = MaterialTheme.colors.onSurface
    val invalidColor = MaterialTheme.colors.secondary
    
    val transition = updateTransition(transitionState = transitionState, label = "tintTransition")
    return transition.animateColor(
        transitionSpec = { tween(durationMillis = 300) }, label = "tintTransitionSpec"
    ) {
        if (it == PeopleUserInputAnimationState.Valid) validColor else invalidColor
    }
}

class PeopleUserInputState {
    var people by mutableStateOf(1)
        private set
    
    val animationState = MutableTransitionState(PeopleUserInputAnimationState.Valid)

    fun addPerson() {
        people = (people % (MAX_PEOPLE + 1)) + 1
        updateAnimationState()
    }

    private fun updateAnimationState() {
        val newState = if(people > MAX_PEOPLE) PeopleUserInputAnimationState.Invalid else PeopleUserInputAnimationState.Valid
        if (animationState.targetState != newState) animationState.targetState = newState
    }
}

enum class PeopleUserInputAnimationState { Valid, Invalid }

@Composable
fun ToDestinationUserInput(
    onToDestinationChanged: (String) -> Unit
) {
    CraneEditableUserInput(
        hint = stringResource(R.string.select_destination_hint),
        caption = stringResource(R.string.select_destination_to_caption),
        vectorImageId = R.drawable.ic_plane,
        onInputChanged = onToDestinationChanged
    )
}

@Composable
fun DatesUserInput(datesSelected: String, onDateSelectionClicked: () -> Unit) {
    CraneUserInput(
        onClick = onDateSelectionClicked,
        caption = if (datesSelected.isEmpty()) stringResource(R.string.select_dates) else null,
        text = datesSelected,
        vectorImageId = R.drawable.ic_calendar
    )
}

@Composable
fun CraneEditableUserInput(
    hint: String,
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null,
    onInputChanged: (String) -> Unit
) {
    var textFieldState by rememberSaveable(stateSaver = TextFieldValue.Companion.Saver) {
        mutableStateOf(TextFieldValue())
    }

    CraneBaseUserInput(
        tintIcon = {
            textFieldState.text.isNotEmpty()
        },
        showCaption = {
            textFieldState.text.isNotEmpty()
        },
        vectorImageId = R.drawable.ic_plane,
        caption = caption
    ) {
        BasicTextField(
            value = textFieldState,
            onValueChange = {
                textFieldState = it
                onInputChanged(textFieldState.text)
            },
            textStyle = MaterialTheme.typography.body1.copy(color = LocalContentColor.current),
            // 游标的颜色
            cursorBrush = SolidColor(LocalContentColor.current),
            decorationBox = { innerTextField ->
                if (hint.isNotEmpty() && textFieldState.text.isEmpty()) {
                    Text(
                        text = hint,
                        style = captionTextStyle.copy(color = LocalContentColor.current)
                    )
                }
                innerTextField()
            }
        )
    }
}