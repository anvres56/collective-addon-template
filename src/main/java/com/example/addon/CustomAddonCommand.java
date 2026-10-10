package com.example.addon;

import me.collective.command.ChatHelper;
import me.collective.command.Command;
import net.minecraft.text.Text;

public class CustomAddonCommand extends Command {
    public CustomAddonCommand() {
        super("addoncmd", "Пример команды из внешнего аддона.", "acmd");
    }

    @Override
    public void execute(String[] args) {
        ChatHelper.sendMessage(Text.literal("§a[ExampleAddon] Команда аддона успешно выполнена!"));
        if (args.length > 0) {
            ChatHelper.sendMessage(Text.literal("§7Переданные аргументы: §e" + String.join(", ", args)));
        }
    }
}
