package me.tippie.customadvancements.advancement.types;

import lombok.Getter;
import lombok.ToString;
import lombok.val;
import me.tippie.customadvancements.CustomAdvancements;
import me.tippie.customadvancements.advancement.AdvancementTree;
import me.tippie.customadvancements.advancement.CAdvancement;
import me.tippie.customadvancements.advancement.InvalidAdvancementException;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Represents a type of an {@link CAdvancement}
 */
@ToString
public abstract class AdvancementType implements Listener {

	/**
	 * The label of this {@link AdvancementType}
	 */
	@Getter private final String label;

	/**
	 * Creates a new {@link AdvancementType}
	 *
	 * @param label label of this type
	 * @see me.tippie.customadvancements.advancement.AdvancementManager#registerAdvancement(AdvancementType)
	 */
	AdvancementType(final String label) {
		this.label = label;
	}

	/**
	 * Registers progress of an {@link AdvancementType}
	 *
	 * @param event      the object of the event this advancement type belongs to
	 * @param playeruuid the uuid of the player who may make progress
	 */
	public void progress(final Object event, final UUID playeruuid) {
		val caPlayer = CustomAdvancements.getCaPlayerManager().getPlayer(playeruuid);
		for (final AdvancementTree tree : CustomAdvancements.getAdvancementManager().getAdvancementTrees()) {
			final List<CAdvancement> advancements = tree.getAdvancements().stream().filter(advancement -> advancement.getType().equals(this.label)).collect(Collectors.toList());
			for (final CAdvancement advancement : advancements) {
				if (caPlayer.checkIfQuestActive(tree.getLabel() + "." + advancement.getLabel())) {
					onProgress(event, advancement.getValue(), tree.getLabel() + "." + advancement.getLabel());
				}
			}
		}
	}

	protected abstract void onProgress(Object event, String value, String path);

	public void progression(final int amount, final String path, final UUID playeruuid) {
		val caPlayer = CustomAdvancements.getCaPlayerManager().getPlayer(playeruuid);
		try {
			caPlayer.updateProgress(path, amount, true);
		} catch (final InvalidAdvancementException ex) {
			CustomAdvancements.getInstance().getLogger().log(Level.SEVERE, "AdvancementType " + this.label + " attempting to add progression to an invalid advancement!");
		}
	}

	/**
	 * Compares string with {@link AdvancementType}
	 *
	 * @param in string of type (label)
	 * @return if type label equals to label of this {@link AdvancementType}
	 */
	public boolean equals(final String in) {
		return this.label.equalsIgnoreCase(in);
	}
}
