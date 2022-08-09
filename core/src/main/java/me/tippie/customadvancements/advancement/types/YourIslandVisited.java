package me.tippie.customadvancements.advancement.types;

import me.tippie.customadvancements.advancement.AdvancementManager;
import me.tippie.customadvancements.util.Lang;
import org.bukkit.event.EventHandler;
import world.bentobox.visit.events.VisitEvent;

public class YourIslandVisited extends AdvancementType<VisitEvent> {

    public YourIslandVisited() {
        super("visited-island", Lang.ADVANCEMENT_TYPE_ISLAND_VISITED_UNIT.getString());
    }

    @EventHandler
    public void onVisit(VisitEvent event) {
        progress(event, event.getPlayer());
    }

    @Override
    protected void onProgress(VisitEvent e, String value, String path) {
        progression(1, path, e.getIsland().getOwner(), false);
    }
}
