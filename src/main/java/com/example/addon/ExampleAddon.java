package com.example.addon;

import me.collective.addon.CollectiveAddon;
import me.collective.addon.AddonInfo;

@AddonInfo(
    name = "Example Addon",
    version = "1.0.0",
    author = "anvres56",
    description = "A reference addon demonstrating custom modules, commands, and events for Collective Client.",
    website = "https://github.com/anvres56/collective-addon-template"
)
public class ExampleAddon extends CollectiveAddon {

    @Override
    public void onInitialize() {
        System.out.println("[ExampleAddon] Initializing Example Addon for Collective Client...");

        // Register custom modules
        registerModule(new CustomAddonModule());

        // Register custom commands
        registerCommand(new CustomAddonCommand());

        System.out.println("[ExampleAddon] Successfully loaded modules and commands!");
    }
}
