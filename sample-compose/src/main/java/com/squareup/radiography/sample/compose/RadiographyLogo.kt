package com.squareup.radiography.sample.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlendMode.Darken
import androidx.compose.ui.graphics.BlendMode.Difference
import androidx.compose.ui.graphics.BlendMode.DstIn
import androidx.compose.ui.graphics.BlendMode.DstOut
import androidx.compose.ui.graphics.BlendMode.DstOver
import androidx.compose.ui.graphics.BlendMode.Lighten
import androidx.compose.ui.graphics.BlendMode.Luminosity
import androidx.compose.ui.graphics.BlendMode.Multiply
import androidx.compose.ui.graphics.BlendMode.Overlay
import androidx.compose.ui.graphics.BlendMode.Saturation
import androidx.compose.ui.graphics.BlendMode.Screen
import androidx.compose.ui.graphics.BlendMode.SrcIn
import androidx.compose.ui.graphics.BlendMode.SrcOut
import androidx.compose.ui.graphics.BlendMode.SrcOver
import androidx.compose.ui.graphics.BlendMode.Xor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@Preview @Composable private fun RadiographyLogoPreview() {
  RadiographyLogo()
}

private val MinSize = 8.dp
private const val NestingScale = .46f
private val NestingOffsetFraction = Offset(.5f, .59f)

/**
 * Draws a Radiography logo, nested until the logo is too small to see.
 */
@Composable fun RadiographyLogo(
  modifier: Modifier = Modifier,
  colorFilter: ColorFilter? = null
) {
  val image = imageResource(R.drawable.logo_unnested)

  // Aspect ratio + wrap are to ensure the outermost invocation has the
  // right positioning if only one axis is constrained.
  Stack(modifier.aspectRatio(1f).wrapContentSize()) {
    // TODO fix colors
    Image(image, colorFilter = colorFilter)
    Canvas {
      this.
    }

    val childModifier = Modifier
        .fillMaxSize(NestingScale)
        .gravity(FractionAlignment(NestingOffsetFraction))

    val minSize = with(DensityAmbient.current) { MinSize.toIntPx() }

    // TODO scanning withConstraints is broken
    WithConstraints(childModifier) {
      val nest = constraints.maxHeight > minSize && constraints.maxWidth > minSize
      if (nest) {
        RadiographyLogo(colorFilter = ColorFilter(Companion.Black, Xor))
      }
    }
  }
}

/** Aligns the composable as horizontal and vertical fractions of its bounds. */
private class FractionAlignment(
  private val offsetFraction: Offset
) : Alignment {
  override fun align(
    size: IntSize,
    layoutDirection: LayoutDirection
  ): IntOffset = IntOffset(
      x = (size.width * offsetFraction.x).roundToInt(),
      y = (size.height * offsetFraction.y).roundToInt()
  )
}
