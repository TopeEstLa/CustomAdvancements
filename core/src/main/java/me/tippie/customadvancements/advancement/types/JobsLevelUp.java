package me.tippie.customadvancements.advancement.types;

import com.gamingmesh.jobs.api.JobsLevelUpEvent;
import lombok.val;
import me.tippie.customadvancements.advancement.AdvancementManager;
import me.tippie.customadvancements.util.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class JobsLevelUp extends AdvancementType<JobsLevelUpEvent> {

    public JobsLevelUp() {
        super("jobs", Lang.ADVANCEMENT_TYPE_JOBS_UNIT.getString());
    }

    @EventHandler
    public void onJobsLevelUp(JobsLevelUpEvent event) {
        progress(event, event.getPlayer().getUniqueId());
    }

    @Override
    protected void onProgress(JobsLevelUpEvent event, String value, String path) {
        Player player = event.getPlayer().getPlayer();

        progression(1, path, player.getUniqueId(), false);
    }
}
