package instance.load;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import strefagier.minecraft.regioninstancesystem;

public class getLocationGreeting {

    private regioninstancesystem regionEnteredEvent;

    public getLocationGreeting(regioninstancesystem regionEnteredEvent) { this.regionEnteredEvent = regionEnteredEvent; }

    public String getGreeting(String location, int status) {
        ConfigurationSection config = regionEnteredEvent.getLocationDataConfig();
        String greeting = "Nie ustawione";

        if ( config.isConfigurationSection(location)  ) {

            ConfigurationSection locationSection = config.getConfigurationSection(location);

            if ( locationSection.isConfigurationSection(String.valueOf(status)) ) {

                ConfigurationSection statusSection = locationSection.getConfigurationSection(String.valueOf(status));

                if ( statusSection.contains("greeting")) {
                    greeting = statusSection.getString("greeting");
                }
            }

        }

        return greeting;
    }

    public String getTitle(String location, int status) {
        ConfigurationSection config = regionEnteredEvent.getLocationDataConfig();
        String title = "Nie ustawione";

        if ( config.isConfigurationSection(location)  ) {

            ConfigurationSection locationSection = config.getConfigurationSection(location);

            if ( locationSection.isConfigurationSection(String.valueOf(status)) ) {

                ConfigurationSection statusSection = locationSection.getConfigurationSection(String.valueOf(status));

                if ( statusSection.contains("title")) {
                    title = statusSection.getString("title");
                }
            }

        }

        return title;
    }

    public String getSubTitle(String location, int status) {
        ConfigurationSection config = regionEnteredEvent.getLocationDataConfig();
        String subtitle = "Nie ustawione";

        if ( config.isConfigurationSection(location)  ) {

            ConfigurationSection locationSection = config.getConfigurationSection(location);

            if ( locationSection.isConfigurationSection(String.valueOf(status)) ) {

                ConfigurationSection statusSection = locationSection.getConfigurationSection(String.valueOf(status));

                if ( statusSection.contains("subtitle")) {
                    subtitle = statusSection.getString("subtitle");
                }
            }

        }

        return subtitle;
    }
}
