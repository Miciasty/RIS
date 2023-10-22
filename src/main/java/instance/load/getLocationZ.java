package instance.load;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import strefagier.minecraft.regioninstancesystem;

public class getLocationZ {

    private regioninstancesystem regionEnteredEvent;

    public getLocationZ(regioninstancesystem regionEnteredEvent) { this.regionEnteredEvent = regionEnteredEvent; }

    public int getZ(String location, int status) {
        ConfigurationSection config = regionEnteredEvent.getLocationDataConfig();
        int z = 0;

        if ( config.isConfigurationSection(location)  ) {

            ConfigurationSection locationSection = config.getConfigurationSection(location);

            if ( locationSection.isConfigurationSection(String.valueOf(status)) ) {

                ConfigurationSection statusSection = locationSection.getConfigurationSection(String.valueOf(status));

                if ( statusSection.contains("z")) {
                    z = statusSection.getInt("z");
                }
            }

        }

        return z;
    }
}
