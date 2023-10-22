package instance.load;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import strefagier.minecraft.regioninstancesystem;

public class getLocationX {

    private regioninstancesystem regionEnteredEvent;

    public getLocationX(regioninstancesystem regionEnteredEvent) { this.regionEnteredEvent = regionEnteredEvent; }

    public int getX(String location, int status) {
        ConfigurationSection config = regionEnteredEvent.getLocationDataConfig();
        int x = 0;

        if ( config.isConfigurationSection(location)  ) {

            ConfigurationSection locationSection = config.getConfigurationSection(location);

            if ( locationSection.isConfigurationSection(String.valueOf(status)) ) {

                ConfigurationSection statusSection = locationSection.getConfigurationSection(String.valueOf(status));

                if ( statusSection.contains("x")) {
                    x = statusSection.getInt("x");
                }
            }

        }

        return x;
    }
}
