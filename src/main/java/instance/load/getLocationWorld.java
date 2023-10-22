package instance.load;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import strefagier.minecraft.regioninstancesystem;

public class getLocationWorld {

    private regioninstancesystem regionEnteredEvent;

    public getLocationWorld(regioninstancesystem regionEnteredEvent) { this.regionEnteredEvent = regionEnteredEvent; }

    public String getWorld(String location, int status) {
        ConfigurationSection config = regionEnteredEvent.getLocationDataConfig();
        String world = "world";

        if ( config.isConfigurationSection(location)  ) {

            ConfigurationSection locationSection = config.getConfigurationSection(location);

            if ( locationSection.isConfigurationSection(String.valueOf(status)) ) {

                ConfigurationSection statusSection = locationSection.getConfigurationSection(String.valueOf(status));

                if ( statusSection.contains("world")) {
                    world = statusSection.getString("world");
                }
            }

        }

        return world;
    }
}
