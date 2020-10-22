package net.runelite.client.plugins.stealingartefacts;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

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
}

