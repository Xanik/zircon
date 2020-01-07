package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import kotlin.math.roundToInt

@Suppress("DuplicatedCode")
class HorizontalScrollBarRenderer : ComponentRenderer<ScrollBar> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<ScrollBar>) {
        val component = context.component
        tileGraphics.applyStyle(context.currentStyle)

        val defaultStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DEFAULT)
        val invertedDefaultStyleSet = defaultStyleSet
                .withBackgroundColor(defaultStyleSet.foregroundColor)
                .withForegroundColor(defaultStyleSet.backgroundColor)
        val disabledStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DISABLED)

        val lowBarPosition = component.currentStep

        var barSizeInSteps = (component.rangeValue / ((component.maxValue - component.minValue).toDouble() / component.size.width.toDouble())).roundToInt()

        val highBarPosition = lowBarPosition + barSizeInSteps - 1
        val totalScrollBarWidth = context.component.contentSize.width

        (0..totalScrollBarWidth).forEach { idx ->
            when {
                idx < lowBarPosition -> tileGraphics.draw(Tile.createCharacterTile(' ', disabledStyleSet), Position.create(idx, 0))
                idx > highBarPosition -> tileGraphics.draw(Tile.createCharacterTile(' ', disabledStyleSet), Position.create(idx, 0))
                else -> tileGraphics.draw(Tile.createCharacterTile(' ', invertedDefaultStyleSet), Position.create(idx, 0))
            }
        }
    }
}
