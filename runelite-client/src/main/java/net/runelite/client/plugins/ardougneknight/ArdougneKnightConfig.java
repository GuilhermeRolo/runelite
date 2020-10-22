package net.runelite.client.plugins.ardougneknight;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("currentworld")
public interface ArdougneKnightConfig extends Config
{
    @ConfigItem(
            position = 1,
            keyName = "ardougneKnightDisplay",
            name = "Highlight Ardougne Knights",
            description = "Toggle the display of the Knight"
    )
    default boolean ardougneKnightDisplay()
    {
        return false;
    }
    @ConfigItem(
            position = 2,
            keyName = "minimumHealth",
            name = "Mininum health ",
            description = "Mininum health before advising to eat"
    )
    default int minimumHealth() {return 20;}

}