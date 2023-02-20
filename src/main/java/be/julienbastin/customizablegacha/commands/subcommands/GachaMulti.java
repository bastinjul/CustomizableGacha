package be.julienbastin.customizablegacha.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class GachaMulti extends SubCommand<Player> {

    public GachaMulti(JavaPlugin javaPlugin) {
        super("/czgacha", javaPlugin);
    }

    @Override
    public @NotNull String getValue() {
        return "multi";
    }

    @Override
    public @NotNull String description() {
        return "Multi Pack Draw";
    }

    @Override
    public void perform(@NotNull Player sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        int quantity = this.plugin.getConfig().getInt("multi-draw.quantity");this.plugin.getConfig().getInt("multi-draw.quantity");
        sender.sendMessage("Configured Quantity : " + quantity);
    }
}
