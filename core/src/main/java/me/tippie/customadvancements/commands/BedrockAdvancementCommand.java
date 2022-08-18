package me.tippie.customadvancements.commands;

import me.tippie.customadvancements.CustomAdvancements;
import me.tippie.customadvancements.advancement.AdvancementTree;
import me.tippie.customadvancements.advancement.CAdvancement;
import me.tippie.customadvancements.player.CAPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.ModalForm;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.ModalFormResponse;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;

public class BedrockAdvancementCommand implements CommandExecutor {

    private final CustomAdvancements customAdvancements;

    public BedrockAdvancementCommand(CustomAdvancements customAdvancements) {
        this.customAdvancements = customAdvancements;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
                SimpleForm.Builder simpleForm = SimpleForm.builder()
                        .title("Bedrock Advancements");

                for (AdvancementTree tree : CustomAdvancements.getAdvancementManager().getAdvancementTrees()) {
                    simpleForm.button(tree.getLabel());
                }

                simpleForm.responseHandler((form, responseData) -> {
                    ModalFormResponse response = (ModalFormResponse) form.parseResponse(responseData);

                    if (!response.isCorrect()) {
                        return;
                    }

                    System.out.println(response.getClickedButtonId());

                    AdvancementTree tree = CustomAdvancements.getAdvancementManager().getAdvancementTrees().get(response.getClickedButtonId());
                    player.sendMessage("You have unlocked the " + tree.getLabel() + " advancement!");
                    player.closeInventory();
                    this.openAdvancementTree(player, tree);
                });


                FloodgateApi.getInstance().getPlayer(player.getUniqueId()).sendForm(simpleForm.build());
            } else {
                sender.sendMessage("You are not Bedrock ");
            }
        } else {
            sender.sendMessage("This command can only be run by a player.");
        }

        return true;
    }

    private void openAdvancementTree(Player player, AdvancementTree tree) {
        SimpleForm.Builder simpleForm = SimpleForm.builder()
                .title(tree.getLabel() + " Advancements");

        CAPlayer caPlayer = CustomAdvancements.getCaPlayerManager().getPlayer(player.getUniqueId());

        for (CAdvancement advancement : tree.getAdvancements()) {
            simpleForm.button(advancement.getDisplayName() + " - " + this.translateComplete(caPlayer, advancement));
        }

        simpleForm.responseHandler((form, responseData) -> {
            ModalFormResponse response = (ModalFormResponse) form.parseResponse(responseData);

            if (!response.isCorrect()) {
                return;
            }

            System.out.println(response.getClickedButtonId());

            CAdvancement advancement = tree.getAdvancements().get(response.getClickedButtonId());
            player.sendMessage("You have unlocked the " + advancement.getDisplayName() + " advancement!");
            player.closeInventory();
            this.openAdvancementInfo(caPlayer, advancement);
        });

        FloodgateApi.getInstance().getPlayer(player.getUniqueId()).sendForm(simpleForm.build());
    }

    private String translateComplete(CAPlayer caPlayer, CAdvancement advancement) {
        return caPlayer.getAdvancementProgress().get(advancement.getPath()).isCompleted() ? "§a✔" : "§e✘";
    }

    private void openAdvancementInfo(CAPlayer caPlayer, CAdvancement advancement) {
        ModalForm modalForm = ModalForm.builder()
                .title(advancement.getDisplayName())
                .content("L'achivement " + advancement.getDisplayName() + " est " + this.translateComplete(caPlayer, advancement) + "\n \n " +
                        caPlayer.getAdvancementProgress().get(advancement.getPath()) + "/" + advancement.getMaxProgress())
                .button1("Close")
                .build();

        FloodgateApi.getInstance().getPlayer(caPlayer.getUuid()).sendForm(modalForm);
    }
}
