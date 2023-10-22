package instance.load;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.chat.BaseComponent;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import strefagier.minecraft.regioninstancesystem;

import javax.security.auth.login.Configuration;
import java.io.IOException;

public class location {

    private regioninstancesystem regionEnteredEvent;

    public location(regioninstancesystem regionEnteredEvent) { this.regionEnteredEvent = regionEnteredEvent; }

    int status = 1;

    public int getProgressStatus(String UUID, String location) {

        ConfigurationSection config = regionEnteredEvent.getPlayerProgressionConfig();

        if ( config.isConfigurationSection(UUID)  ) {

            ConfigurationSection uuidSection = config.getConfigurationSection(UUID);

            if ( uuidSection.contains(location) ) {
                status = uuidSection.getInt(location);
            } else {
                uuidSection.set(location, 1);

                try {
                    regionEnteredEvent.getPlayerProgressionConfig().save(regionEnteredEvent.getPlayerProgressionFile());
                    Bukkit.getServer().getConsoleSender().sendMessage("§5[§dRIS§5] §aNew location for player with UUID §9" + UUID + " §ahas been registred");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            status = uuidSection.getInt(location);
        } else {

            ConfigurationSection newSection = config.createSection(UUID);
            newSection.set(location, 1);

            try {
                ((FileConfiguration) config).save(regionEnteredEvent.getPlayerProgressionFile());
                Bukkit.getServer().getConsoleSender().sendMessage("§5[§dRIS§5] §aNew player is now registered in files with UUID §9" + UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    public boolean sendToLocation(Player player, String location, int status) {

        getLocationWorld World = new getLocationWorld(regionEnteredEvent);
        getLocationX X = new getLocationX(regionEnteredEvent);
        getLocationY Y = new getLocationY(regionEnteredEvent);
        getLocationZ Z = new getLocationZ(regionEnteredEvent);
        getLocationGreeting greeting = new getLocationGreeting(regionEnteredEvent);

        String title = greeting.getTitle(location, status);
        String subtitle = greeting.getSubTitle(location, status);

        Component C_title = MiniMessage.miniMessage().deserialize("<gradient:#f9bf3b:#e67e5e:#e74c3c>"+title);
        Component C_subtitle = MiniMessage.miniMessage().deserialize("<gradient:#89FF77:#7BBC71>"+subtitle);
        Component warning = MiniMessage.miniMessage().deserialize(
                "<#ff3100>\n ⚠ Ostrzeżenie! \n<#f28762> Wykryto błąd #L1, prosimy zgłosić to do administratora.\n");

        String world = World.getWorld(location, status);
        int x = X.getX(location, status);
        int y = Y.getY(location, status);
        int z = Z.getZ(location, status);

        if ( !(x == 0 && y == 0 && z == 0) ) {
            Location TeleportHere = new Location(Bukkit.getWorld(world), x, y, z);
            player.teleport(TeleportHere);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 14, 1));
            player.playSound(player, Sound.AMBIENT_CAVE, 1, 1);
            player.showTitle(Title.title(C_title, C_subtitle));
        } else {
            player.sendMessage(warning);
        }

        return true;
    }


}
