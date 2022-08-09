package me.tippie.customadvancements.advancement.types;

import me.tippie.customadvancements.util.Lang;
import org.bukkit.event.EventHandler;
import world.bentobox.visit.events.VisitEvent;

public class IslandVisit extends AdvancementType<VisitEvent> {

    public IslandVisit() {
        super("you-visit-island", Lang.ADVANCEMENT_TYPE_YOU_VISIT_UNIT.getString());
    }

    @EventHandler
    public void onVisit(VisitEvent event) {
        progress(event, event.getPlayer());
    }

    @Override
    protected void onProgress(VisitEvent e, String value, String path) {
        progression(1, path, e.getPlayer(), false);
    }
}
