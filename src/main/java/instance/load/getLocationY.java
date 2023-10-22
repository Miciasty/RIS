package instance.load;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import strefagier.minecraft.regioninstancesystem;

public class getLocationY {

    private regioninstancesystem regionEnteredEvent;

    public getLocationY(regioninstancesystem regionEnteredEvent) { this.regionEnteredEvent = regionEnteredEvent; }

    public int getY(String location, int status) {
        ConfigurationSection config = regionEnteredEvent.getLocationDataConfig();
        int y = 0;

        if ( config.isConfigurationSection(location)  ) {

            ConfigurationSection locationSection = config.getConfigurationSection(location);

            if ( locationSection.isConfigurationSection(String.valueOf(status)) ) {

                ConfigurationSection statusSection = locationSection.getConfigurationSection(String.valueOf(status));

                if ( statusSection.contains("y")) {
                    y = statusSection.getInt("y");
                }
            }

        }

        return y;
    }
}
