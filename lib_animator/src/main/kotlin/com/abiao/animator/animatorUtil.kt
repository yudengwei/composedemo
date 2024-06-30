package com.abiao.animator

import android.animation.ValueAnimator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp

@Composable
fun rememberAnimatorOfInt(state: MutableState<Int>, vararg values: Int): ValueAnimator {
    return remember { animatorOfInt(state, *values) }
}

@Composable
fun rememberAnimatorOfColor(state: MutableState<Color>, values: Array<Color>): ValueAnimator {
    return remember { animatorOfColor(state, values) }
}

@Composable
fun rememberAnimatorOfDp(state: MutableState<Dp>, values: Array<Dp>): ValueAnimator {
    return remember { animatorOfDb(state, values) }
}

@Composable
fun rememberAnimatorOfFloat(state: MutableState<Float>, vararg values: Float): ValueAnimator {
    return remember { animatorOfFloat(state, *values) }
}

fun animatorOfInt(state: MutableState<Int>, vararg value: Int): ValueAnimator {
    val animator = ValueAnimator.ofInt(*value)
    animator.addUpdateListener {
        state.value = it.animatedValue as Int
    }
    return animator
}

fun animatorOfDb(state: MutableState<Dp>, value: Array<Dp>): ValueAnimator {
    val dps : FloatArray = value.map { it.value }.toFloatArray()
    val animator = ValueAnimator.ofFloat(*dps)
    animator.addUpdateListener {
        state.value = Dp(it.animatedValue as Float)
    }
    return animator
}

fun animatorOfColor(state: MutableState<Color>, values: Array<Color>): ValueAnimator {
    val colors = values.map { it.toArgb() }.toIntArray()
    val animator = ValueAnimator.ofArgb(*colors)
    animator.addUpdateListener {
        state.value = Color(it.animatedValue as Int)
    }
    return animator
}

fun animatorOfFloat(state: MutableState<Float>, vararg values: Float): ValueAnimator {
    val animator = ValueAnimator.ofFloat(*values)
    animator.addUpdateListener {
        state.value = it.animatedValue as Float
    }
    return animator
}

class IntState(private val state: MutableState<Int>) {

    var value: Int = state.value
        get() = state.value
        set(value) {
            field = value
            state.value = value
        }
}

fun mutableIntStateOf(
    value: Int,
    policy: SnapshotMutationPolicy<Int> = structuralEqualityPolicy()
): IntState {
    val state = mutableStateOf(value, policy)
    return IntState(state)
}