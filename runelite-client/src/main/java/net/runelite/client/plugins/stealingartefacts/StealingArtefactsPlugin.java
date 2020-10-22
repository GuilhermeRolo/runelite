package net.runelite.client.plugins.stealingartefacts;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

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

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private StealingArtefactsOverlay overlay;

    @Getter(AccessLevel.PACKAGE)
    private final Set<NPC> highlightedNpcs = new HashSet<>();


    @Provides
    StealingArtefactsConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(StealingArtefactsConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(overlay);
        highlightedNpcs.clear();
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        if(config.highlightTargets()) {
            for(NPC npc: client.getNpcs()){
                final String npcName = npc.getName();
                if(npcName != null && npcName.contains("Patrolman") || npcName.contains("Patrolwoman")) {
                    highlightedNpcs.add(npc);
                }
            }
        } else {
            highlightedNpcs.clear();
        }
    }

}