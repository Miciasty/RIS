package strefagier.minecraft;

import instance.load.getLocationGreeting;
import instance.load.location;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.raidstone.wgevents.WorldGuardEvents;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public final class regioninstancesystem extends JavaPlugin implements Listener {

    private File PlayerProgression = new File(getDataFolder(), "players/progression.yml");
    private FileConfiguration PlayerProgressionConfig = YamlConfiguration.loadConfiguration(PlayerProgression);

    public FileConfiguration getPlayerProgressionConfig() {
        return PlayerProgressionConfig;
    }
    public File getPlayerProgressionFile() {
        return PlayerProgression;
    }



    // --------------------------------------------------------------------------------------------------------//
    private File LocationData = new File(getDataFolder(), "instances/locations.yml");
    private FileConfiguration LocationDataConfig = YamlConfiguration.loadConfiguration(LocationData);

    public FileConfiguration getLocationDataConfig() {
        return LocationDataConfig;
    }

    public File getLocationData() {
        return LocationData;
    }

    String TacticalUUID;
    String TacticalLocation;
    int TacticalStatus = 1;

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this,this);

        Component IR_L1 = MiniMessage.miniMessage().deserialize("<gradient:#9953aa:#172d5d> ___   ____      ____   __   __  ____    _____   _____   __  __ ");
        Component IR_L2 = MiniMessage.miniMessage().deserialize("<gradient:#9953aa:#172d5d>|_ _| |  _ \\    / ___|  \\ \\ / / / ___|  |_   _| | ____| |  \\/  |");
        Component IR_L3 = MiniMessage.miniMessage().deserialize("<gradient:#9953aa:#172d5d> | |  | |_) |   \\___ \\   \\ V /  \\___ \\    | |   |  _|   | |\\/| |");
        Component IR_L4 = MiniMessage.miniMessage().deserialize("<gradient:#9953aa:#172d5d> | |  |  _ <     ___) |   | |    ___) |   | |   | |___  | |  | |");
        Component IR_L5 = MiniMessage.miniMessage().deserialize("<gradient:#9953aa:#172d5d>|___| |_| \\_\\   |____/    |_|   |____/    |_|   |_____| |_|  |_|");

        getServer().getConsoleSender().sendMessage(" ");
        getServer().getConsoleSender().sendMessage(IR_L1);
        getServer().getConsoleSender().sendMessage(IR_L2);
        getServer().getConsoleSender().sendMessage(IR_L3);
        getServer().getConsoleSender().sendMessage(IR_L4);
        getServer().getConsoleSender().sendMessage(IR_L5);
        getServer().getConsoleSender().sendMessage(" ");
        getServer().getConsoleSender().sendMessage(" ");

        if (!PlayerProgression.exists()) {
            saveResource("players/progression.yml", false);
            Component RIS = MiniMessage.miniMessage().deserialize("<gradient:#9953aa:#172d5d>[RIS] <#006FFF>Players data file has been created.");
            getServer().getConsoleSender().sendMessage( RIS );
        }
        if (!LocationData.exists()) {
            saveResource("instances/locations.yml", false);
            Component RIS = MiniMessage.miniMessage().deserialize("<gradient:#9953aa:#172d5d>[RIS] <#006FFF>Locations file has been created. Please restart the server!");
            getServer().getConsoleSender().sendMessage( RIS );
        }

        getCommand("locationdecision").setExecutor(new LocationDecisionExecutor());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegionEnter(RegionEnteredEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;

        String uuid = event.getUUID().toString();
        String regionName = event.getRegionName();


        if (regionName.contains("ri_")) {

            TacticalUUID = uuid;
            TacticalLocation = regionName;

            location l = new location(this);
            getLocationGreeting g = new getLocationGreeting(this);

            TacticalStatus = l.getProgressStatus(uuid, regionName);
            String greeting = g.getGreeting(TacticalLocation, TacticalStatus);

            Component enterMessage = MiniMessage.miniMessage()
                    .deserialize("<blue>⚐ " + greeting + "</blue> \n" +
                            "<click:run_command:'/locationdecision tak '><hover:show_text:'<green>Zostaniesz przeteleportowany do tej lokacji</green>'><green>✔ Wchodzę </green></hover></click>" +
                            "<gray> | </gray>" +
                            "<click:run_command:'/locationdecision nie '><hover:show_text:'<gray>W sumie to nic nie robi, ale masz wybór :)</gray>'><red> ❌ Nie wchodzę </red></hover></click>");

            player.sendMessage(enterMessage);

        }
    }

    /*@EventHandler(priority = EventPriority.HIGHEST)
    public void onRegionLeave(RegionLeftEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;

        String regionName = event.getRegionName();

        oAuth = String.valueOf(UUID.randomUUID());
    }*/

    public class LocationDecisionExecutor implements CommandExecutor {

        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            //if (Objects.equals(Auth, oAuth)) {
                if (WorldGuardEvents.isPlayerInAnyRegion(UUID.fromString(TacticalUUID),TacticalLocation)) {
                    if (command.getName().equalsIgnoreCase("locationdecision")) {

                        if (sender instanceof Player) {
                            Player player = (Player) sender;

                            if (args.length == 1) {
                                String decision = args[0].toLowerCase();

                                location l = new location(regioninstancesystem.this);


                                if (decision.equals("tak")) {
                                    l.sendToLocation(player, TacticalLocation, TacticalStatus);
                                }

                            }
                        }

                    }

                } else {
                    Component warning = MiniMessage.miniMessage().deserialize("<gradient:#d14760:#d55988> ❌ Nawet nie znajdujesz się w regionie!");

                    sender.sendMessage("§9 ");
                    sender.sendMessage(warning);
                    sender.sendMessage("§9 ");
                }
            //} else {
            //    sender.sendMessage("\n§c❌ Ta operacja jest nie zweryfikowana systemem auth.\n");
            //}
            //oAuth = String.valueOf(UUID.randomUUID());
            return false;
        }
    }

}


