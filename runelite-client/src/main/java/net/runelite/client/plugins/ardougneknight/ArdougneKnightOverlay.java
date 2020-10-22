package net.runelite.client.plugins.ardougneknight;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import java.awt.*;

@Slf4j

public class ArdougneKnightOverlay  extends Overlay {

    private final Client client;
    private final ArdougneKnightConfig config;

    @Getter
    @Setter
    public Color color;

    @Getter
    @Setter
    public NPC npc;

    @Inject
    private ArdougneKnightOverlay(Client client, ArdougneKnightConfig config)
    {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);

    }

    @Override
    public Dimension render(Graphics2D graphics) {

        renderNpcOverlay(graphics,color);

        return null;
    }

    private void renderNpcOverlay(Graphics2D graphics, Color color)
    {
        if(npc != null)
        {
            Shape objectClickbox = npc.getConvexHull();
            renderPoly(graphics, objectClickbox,color);
        }
    }

    private void renderPoly(Graphics2D graphics, Shape polygon, Color color)
    {
        if (polygon != null)
        {
            graphics.setColor(color);
            graphics.setStroke(new BasicStroke(2));
            graphics.draw(polygon);
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
            graphics.fill(polygon);
        }
    }

}
