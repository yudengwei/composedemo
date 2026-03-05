package com.abiao.animator

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

private enum class DemoType {
    ORBITAL_CARD,
    FLIP_CARD,
    PHYSICS_SLIDER,
    FAN_MENU
}

@Composable
fun ComplexAnimationGallery(modifier: Modifier = Modifier) {
    val demos = remember {
        listOf(
            DemoType.ORBITAL_CARD to "Orbital Neon Card",
            DemoType.FLIP_CARD to "3D Flip Info Card",
            DemoType.PHYSICS_SLIDER to "Physics Snap Slider",
            DemoType.FAN_MENU to "Fan Menu"
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                text = "Compose Complex Animations",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        items(demos) { (type, title) ->
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                when (type) {
                    DemoType.ORBITAL_CARD -> OrbitalNeonCard()
                    DemoType.FLIP_CARD -> FlipInfoCard()
                    DemoType.PHYSICS_SLIDER -> PhysicsSnapSlider()
                    DemoType.FAN_MENU -> FanMenuDemo()
                }
            }
        }
    }
}

@Composable
private fun OrbitalNeonCard() {
    var expanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = expanded, label = "orbital-card")
    val cardHeight by transition.animateDp(
        label = "card-height",
        transitionSpec = { spring(dampingRatio = 0.75f, stiffness = Spring.StiffnessLow) }
    ) { isExpanded ->
        if (isExpanded) 220.dp else 140.dp
    }
    val corner by transition.animateDp(label = "card-corner") { if (it) 28.dp else 18.dp }
    val contentOffsetY by transition.animateDp(
        label = "content-offset",
        transitionSpec = { spring(stiffness = Spring.StiffnessMediumLow) }
    ) { if (it) 0.dp else 8.dp }
    val backgroundColor by transition.animateColor(
        label = "background-color",
        transitionSpec = { tween(durationMillis = 550) }
    ) { isExpanded ->
        if (isExpanded) Color(0xFF11193B) else Color(0xFF1A1D25)
    }

    val infinite = rememberInfiniteTransition(label = "orbital-infinite")
    val orbitAngle by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7000, easing = LinearEasing)
        ),
        label = "orbit-angle"
    )
    val pulse by infinite.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "orbit-pulse"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(cardHeight)
            .clickable { expanded = !expanded }
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val baseRadius = size.minDimension * 0.28f * pulse
                    for (i in 0 until 4) {
                        val angle = (orbitAngle + i * 90f) * PI / 180f
                        val orbitRadius = if (expanded) size.minDimension * 0.33f else size.minDimension * 0.26f
                        val nodeCenter = Offset(
                            x = center.x + cos(angle).toFloat() * orbitRadius,
                            y = center.y + sin(angle).toFloat() * orbitRadius
                        )
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF57D9FF).copy(alpha = 0.70f),
                                    Color.Transparent
                                ),
                                center = nodeCenter,
                                radius = baseRadius
                            ),
                            radius = baseRadius,
                            center = nodeCenter
                        )
                    }
                }
            },
        shape = RoundedCornerShape(corner),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
                .offset(y = contentOffsetY),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Orbital Core",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            AnimatedContent(
                targetState = expanded,
                transitionSpec = {
                    (slideInHorizontally { width -> width / 4 } + fadeIn(tween(220))).togetherWith(
                        slideOutHorizontally { width -> -width / 4 } + fadeOut(tween(220))
                    ).using(SizeTransform(clip = false))
                },
                label = "orbital-text"
            ) { isExpanded ->
                Text(
                    text = if (isExpanded) {
                        "Transition + Infinite + Canvas in one component. Tap to collapse."
                    } else {
                        "Tap to expand and inspect the combined animation layers."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }
    }
}

@Composable
private fun FlipInfoCard() {
    var flipped by remember { mutableStateOf(false) }
    val density = LocalDensity.current.density
    val cardRotationY by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = spring(dampingRatio = 0.70f, stiffness = Spring.StiffnessLow),
        label = "flip-rotation"
    )
    val frontAlpha by animateFloatAsState(
        targetValue = if (cardRotationY <= 90f) 1f else 0f,
        animationSpec = tween(180),
        label = "front-alpha"
    )
    val backAlpha by animateFloatAsState(
        targetValue = if (cardRotationY > 90f) 1f else 0f,
        animationSpec = tween(180),
        label = "back-alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .graphicsLayer(rotationY = cardRotationY, cameraDistance = 12f * density)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF18222E))
            .clickable { flipped = !flipped }
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(frontAlpha),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Order Paid", style = MaterialTheme.typography.titleLarge, color = Color.White)
            Text(
                "Tap to flip",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF9AD8FF)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(rotationY = 180f)
                .alpha(backAlpha),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text("No. 9F3A-2D81", style = MaterialTheme.typography.titleMedium, color = Color.White)
            Text(
                "3D rotation + two-sided alpha switching.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFC9D6E2)
            )
        }
    }
}

@Composable
private fun PhysicsSnapSlider() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF2F6FB))
            .padding(horizontal = 14.dp, vertical = 18.dp)
    ) {
        val density = LocalDensity.current
        val knobSize = 56.dp
        val knobSizePx = with(density) { knobSize.toPx() }
        val maxOffset = with(density) { (maxWidth - knobSize).toPx().coerceAtLeast(0f) }
        val anchors = remember(maxOffset) { listOf(0f, maxOffset * 0.5f, maxOffset) }

        val scope = rememberCoroutineScope()
        val decay = rememberSplineBasedDecay<Float>()
        val offsetX = remember { Animatable(0f) }

        LaunchedEffect(maxOffset) {
            offsetX.updateBounds(lowerBound = 0f, upperBound = maxOffset)
        }

        val draggableState = rememberDraggableState { delta ->
            scope.launch {
                offsetX.snapTo((offsetX.value + delta).coerceIn(0f, maxOffset))
            }
        }

        val progress = if (maxOffset <= 0f) 0f else (offsetX.value / maxOffset).coerceIn(0f, 1f)
        val activeWidth = with(density) { (progress * maxOffset + knobSizePx / 2f).toDp() }

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text(
                text = "Release with velocity. It decays and snaps to nearest anchor.",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF4A6174)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(knobSize)
            ) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxWidth()
                        .height(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFDCE7F4)
                ) {}

                Surface(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .height(8.dp)
                        .width(activeWidth.coerceAtLeast(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF4F8CFF)
                ) {}

                Box(
                    modifier = Modifier
                        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                        .size(knobSize)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                        .background(Color(0xFF0F6BFE))
                        .draggable(
                            state = draggableState,
                            orientation = Orientation.Horizontal,
                            onDragStopped = { velocity ->
                                scope.launch {
                                    offsetX.animateDecay(velocity, decay)
                                    val snapTarget = anchors.minByOrNull { abs(it - offsetX.value) } ?: 0f
                                    offsetX.animateTo(
                                        targetValue = snapTarget,
                                        initialVelocity = velocity * 0.25f,
                                        animationSpec = spring(
                                            dampingRatio = 0.78f,
                                            stiffness = Spring.StiffnessMediumLow
                                        )
                                    )
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${(progress * 100).roundToInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun FanMenuDemo() {
    val actions = remember { listOf("Create", "Edit", "Share") }
    var expanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = expanded, label = "fan-menu")
    val rotation by transition.animateFloat(
        label = "fab-rotation",
        transitionSpec = { spring(stiffness = Spring.StiffnessMedium) }
    ) { isExpanded -> if (isExpanded) 45f else 0f }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF0B1020))
            .padding(18.dp)
    ) {
        Text(
            text = "Tap the + button to expand actions.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFA8B3C7),
            modifier = Modifier.align(Alignment.TopStart)
        )

        actions.forEachIndexed { index, label ->
            val distance by transition.animateDp(
                label = "distance-$index",
                transitionSpec = {
                    spring(
                        dampingRatio = 0.68f,
                        stiffness = Spring.StiffnessLow
                    )
                }
            ) { isExpanded ->
                if (isExpanded) ((index + 1) * 62).dp else 0.dp
            }
            val alpha by transition.animateFloat(
                label = "alpha-$index",
                transitionSpec = { tween(durationMillis = 220 + index * 60) }
            ) { isExpanded ->
                if (isExpanded) 1f else 0f
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = -distance * 0.65f, y = -distance)
                    .alpha(alpha)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1E2A44))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        FloatingActionButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .graphicsLayer(rotationZ = rotation)
        ) {
            Text("+")
        }
    }
}
