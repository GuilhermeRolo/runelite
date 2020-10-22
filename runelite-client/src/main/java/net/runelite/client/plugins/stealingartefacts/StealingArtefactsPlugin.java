package net.runelite.client.plugins.stealingartefacts;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@PluginDescriptor(
        name = "Stealing Artefacts",
        description = "Show additional options for stealing artefacts",
        tags = {"minigame", "thieving"}
)
@Slf4j
public class StealingArtefactsPlugin extends Plugin {

    @Inject
    private StealingArtefactsConfig config;

    @Inject
    private Client client;

    @Provides
    StealingArtefactsConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(StealingArtefactsConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        // super.startUp();
    }

    @Override
    protected void shutDown() throws Exception {
        // super.shutDown();
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        if(config.highlightTargets()) {
            this.client.getNpcs().forEach(npc -> {
                if(npc.getId() == 6980){
                    npc.getConvexHull();
                }
            });
        }
    }

}