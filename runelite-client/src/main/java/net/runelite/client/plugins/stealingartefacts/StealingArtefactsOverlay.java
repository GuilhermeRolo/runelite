package net.runelite.client.plugins.stealingartefacts;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import java.awt.*;

public class StealingArtefactsOverlay extends Overlay {

    private final Client client;
    private final StealingArtefactsConfig config;
    private final StealingArtefactsPlugin plugin;

    @Getter
    @Setter
    public Color color;

    @Getter
    @Setter
    public NPC npc;

    @Inject
    private StealingArtefactsOverlay(Client client, StealingArtefactsConfig config, StealingArtefactsPlugin plugin)
    {
        this.client = client;
        this.config = config;
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);

    }

    @Override
    public Dimension render(Graphics2D graphics) {

        for (NPC npc: plugin.getHighlightedNpcs()){
            renderNpcOverlay(graphics, config.highlightTargetsColor(), npc);
        }

        return null;
    }

    private void renderNpcOverlay(Graphics2D graphics, Color color, NPC npc)
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
