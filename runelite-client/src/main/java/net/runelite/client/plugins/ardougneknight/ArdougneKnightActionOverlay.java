package net.runelite.client.plugins.ardougneknight;

import lombok.Getter;
import lombok.Setter;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;

class ArdougneKnightActionOverlay extends Overlay
{
    private final ArdougneKnightConfig config;
    private final PanelComponent panelComponent = new PanelComponent();

    @Getter
    @Setter
    public String action = "Unknown";

    @Inject
    private ArdougneKnightActionOverlay( ArdougneKnightConfig config)
    {
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        panelComponent.getChildren().clear();
        String overlayTitle = "Pickpocket action";

        // Build overlay title
        panelComponent.getChildren().add(TitleComponent.builder()
                .text(overlayTitle)
                .color(Color.GREEN)
                .build());

        // Set the size of the overlay (width)
        panelComponent.setPreferredSize(new Dimension(
                graphics.getFontMetrics().stringWidth(overlayTitle) + 30,
                0));

        // Add a line on the overlay for action
        panelComponent.getChildren().add(LineComponent.builder()
                .left("Action:")
                .right(action)
                .build());

        return panelComponent.render(graphics);
    }
}