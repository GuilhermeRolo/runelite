package net.runelite.client.plugins.stealingartefacts;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("stealingartefacts")
public interface StealingArtefactsConfig extends Config {

    @ConfigItem(
            position = 1,
            keyName = "highlightTargets",
            name = "Highlight Targets",
            description = "Highlight patrolman and patrolwoman NPC nearby"
    )
    default boolean highlightTargets()
    {
        return false;
    }
    @ConfigItem(
            position = 2,
            keyName = "highlightTargetsColor",
            name = "Highlight Targets Color",
            description = "Changes the color of the highlighted targets"
    )
    default Color highlightTargetsColor() {return Color.red;}
}

