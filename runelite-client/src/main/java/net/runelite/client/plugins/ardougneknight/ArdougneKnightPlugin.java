package net.runelite.client.plugins.ardougneknight;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
@Slf4j

@PluginDescriptor(
        name = "Ardougne Knight",
        description = "Highlight Ardougne Knights",
        tags = {"thieving", "overlay"}
)
public class ArdougneKnightPlugin extends Plugin {

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ArdougneKnightOverlay overlay;

    @Inject
    private ArdougneKnightActionOverlay actionOverlay;


    @Inject
    private ArdougneKnightConfig config;

    @javax.inject.Inject
    private Notifier notifier;

    @javax.inject.Inject
    private Client client;

    private long stunDate = System.currentTimeMillis();
    private ExecutorService executorService;

    private enum Action
    {
        Heal,
        WaitStun,
        Pick,
        Unknown
    }
    @Provides
    ArdougneKnightConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ArdougneKnightConfig.class);
    }

    @Override
    protected void startUp() throws Exception
    {
        executorService = Executors.newSingleThreadExecutor();
        overlayManager.add(overlay);
        overlayManager.add(actionOverlay);
    }

    @Subscribe
    public void onGameTick(GameTick event){
        Action action = Action.Pick;

        if(checkHealth())
        {
            action = Action.Heal;
        }
        if(System.currentTimeMillis() - stunDate < 2000)
        {
            action = Action.WaitStun;
        }
        switch (action)
        {
            case Heal:
                actionOverlay.action = "Heal";
                overlay.color = Color.red;
                break;
            case Pick:
                actionOverlay.action = "Pick";
                overlay.color = Color.GREEN;
                NPC npc = GetClosestNPC();
                overlay.npc = npc;
                break;
            case WaitStun:
                actionOverlay.action = "Wait";
                overlay.color = Color.BLACK;
        }
    }

    private NPC GetClosestNPC(){
        Player player  = client.getLocalPlayer();
        NPC closestNPC = null;
        Integer closestNPCDist = Integer.MAX_VALUE;
        for (NPC npc : client.getNpcs()) {
            final String npcName = npc.getName();
            if(npcName != null && npcName.contains("Knight of Ardougne"))
            {
                Integer dist = player.getWorldLocation().distanceTo(npc.getWorldLocation());
                if(dist < closestNPCDist)
                {
                    closestNPC = npc;
                    closestNPCDist = dist;
                }
            }
        }
        if(closestNPC != null)
        {
            overlay.npc = closestNPC;
        }
        return null;
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        if (event.getType() != ChatMessageType.GAMEMESSAGE && event.getType() != ChatMessageType.SPAM)
        {
            return;
        }

        String chatMsg = Text.removeTags(event.getMessage()); //remove color and linebreaks

        if(chatMsg.startsWith("You fail to pick the knight's pocket."))
        {
            stunDate = System.currentTimeMillis();
        }
    }

    private Boolean checkHealth() {
        if(client.getBoostedSkillLevel(Skill.HITPOINTS) < config.minimumHealth())
        {
            return true;
        }
        return false;
    }
    @Override
    protected void shutDown() throws Exception
    {
        executorService.shutdown();
        overlayManager.remove(overlay);
        overlayManager.remove(actionOverlay);
    }
}
