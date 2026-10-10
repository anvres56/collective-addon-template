package com.example.addon;

import me.collective.category.Category;
import me.collective.event.EventHandler;
import me.collective.event.events.TickEvent;
import me.collective.module.Module;
import me.collective.setting.BooleanSetting;
import me.collective.setting.NumberSetting;

public class CustomAddonModule extends Module {
    public final BooleanSetting chatNotify = add(new BooleanSetting("ChatNotify", true));
    public final NumberSetting tickInterval = add(new NumberSetting("Interval", 20.0, 1.0, 100.0, 1.0));

    private int timer = 0;

    public CustomAddonModule() {
        super("AddonModule", "Пример модуля, добавленного через внешний аддон для Collective Client.", Category.MISC);
    }

    @Override
    public void onEnable() {
        info("Модуль аддона успешно включен!");
        timer = 0;
    }

    @Override
    public void onDisable() {
        info("Модуль аддона выключен.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null) return;

        timer++;
        if (timer >= tickInterval.getIntValue()) {
            timer = 0;
            if (chatNotify.getValue()) {
                info("Тик аддона сработал! Текущее здоровье: §a%.1f", mc.player.getHealth());
            }
        }
    }
}
