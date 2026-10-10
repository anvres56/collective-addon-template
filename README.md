# Collective Client — Addon Template & Documentation

Официальный шаблон и подробное руководство по разработке аддонов для **Collective Client** (Minecraft 1.21.11 Fabric).

Система аддонов Collective спроектирована как эволюция архитектуры Meteor Client:
- **Двойная загрузка**:
  1. **Fabric Mod Entrypoint** — аддон упаковывается как стандартный мод Fabric с точкой входа `"collective"`.
  2. **Динамическая загрузка JAR** — аддон можно просто поместить в папку `.minecraft/collective/addons/`, и клиент подгрузит его на лету через изолированный URLClassLoader без перезапуска лаунчера!
- **Автоматическая регистрация** модулей, вкладок, команд и настроек.
- **Поддержка рефлексии и событийной шины `@EventHandler`**.

---

## 📁 Структура Проекта

```text
collective-addon-template/
├── build.gradle                               # Конфигурация сборки Loom Fabric
├── settings.gradle                            # Настройки Gradle
├── libs/
│   └── collective-dev.jar                     # API библиотека Collective Client
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/addon/
│       │       ├── ExampleAddon.java          # Главный класс аддона (точка входа)
│       │       ├── CustomAddonModule.java     # Пример кастомного модуля
│       │       └── CustomAddonCommand.java    # Пример кастомной команды чата
│       └── resources/
│           └── fabric.mod.json                # Манифест мода Fabric
```

---

## 🚀 Быстрый старт

### 1. Клонирование репозитория
```bash
git clone https://github.com/anvres56/collective-addon-template.git my-collective-addon
cd my-collective-addon
```

### 2. Сборка аддона
```bash
./gradlew build
```
Собранный файл будет находиться в директории `build/libs/collective-example-addon-1.0.0.jar`.

### 3. Установка аддона
У вас есть два равноценных способа:
1. **Как обычный мод**: поместите `.jar` в папку `.minecraft/mods/` вместе с Collective Client.
2. **Через папку аддонов клиента**: поместите `.jar` в `.minecraft/collective/addons/`. В игре выполните команду:
   ```
   .addon reload
   ```

---

## 🛠 Создание своего Аддона: Руководство

### 1. Главный класс (`CollectiveAddon`)
Создайте класс, расширяющий `CollectiveAddon`, и добавьте аннотацию `@AddonInfo`:

```java
package com.example.addon;

import me.collective.addon.CollectiveAddon;
import me.collective.addon.AddonInfo;

@AddonInfo(
    name = "My Super Addon",
    version = "1.0.0",
    author = "YourName",
    description = "Крутые функции для Collective Client!",
    website = "https://github.com/YourName/my-addon"
)
public class MyAddon extends CollectiveAddon {

    @Override
    public void onInitialize() {
        // Регистрация модулей
        registerModule(new CustomAddonModule());

        // Регистрация команд
        registerCommand(new CustomAddonCommand());
    }
}
```

### 2. Создание Кастомного Модуля (`Module`)
Все модули наследуются от `me.collective.module.Module`:

```java
package com.example.addon;

import me.collective.category.Category;
import me.collective.event.EventHandler;
import me.collective.event.events.TickEvent;
import me.collective.module.Module;
import me.collective.setting.BooleanSetting;
import me.collective.setting.NumberSetting;

public class CustomAddonModule extends Module {
    // Настройки отображаются в ClickGUI автоматически
    public final BooleanSetting chatNotify = add(new BooleanSetting("ChatNotify", true));
    public final NumberSetting tickInterval = add(new NumberSetting("Interval", 20.0, 1.0, 100.0, 1.0));

    private int timer = 0;

    public CustomAddonModule() {
        super("AddonModule", "Описание модуля в интерфейсе", Category.MISC);
    }

    @Override
    public void onEnable() {
        info("Модуль включен!");
        timer = 0;
    }

    @Override
    public void onDisable() {
        info("Модуль выключен!");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null) return;

        timer++;
        if (timer >= tickInterval.getIntValue()) {
            timer = 0;
            if (chatNotify.getValue()) {
                info("Здоровье игрока: §a%.1f", mc.player.getHealth());
            }
        }
    }
}
```

### 3. Создание Кастомной Команды (`Command`)
```java
package com.example.addon;

import me.collective.command.ChatHelper;
import me.collective.command.Command;
import net.minecraft.text.Text;

public class CustomAddonCommand extends Command {
    public CustomAddonCommand() {
        super("addoncmd", "Описание команды аддона", "acmd");
    }

    @Override
    public void execute(String[] args) {
        ChatHelper.sendMessage(Text.literal("§a[MyAddon] Команда успешно вызвана!"));
        if (args.length > 0) {
            ChatHelper.sendMessage(Text.literal("§7Аргументы: §e" + String.join(", ", args)));
        }
    }
}
```

### 4. Настройка `fabric.mod.json`
В блоке `entrypoints` укажите точку входа `"collective"`:

```json
{
  "schemaVersion": 1,
  "id": "my-addon",
  "version": "1.0.0",
  "name": "My Super Addon",
  "entrypoints": {
    "collective": [
      "com.example.addon.MyAddon"
    ]
  }
}
```

---

## 🎮 Команды управления аддонами в игре

- `.addon list` — Показывает все загруженные аддоны, их версии, авторов и описания.
- `.addon reload` — Перезагружает и сканирует папку `.minecraft/collective/addons/`.
- `.addon folder` — Открывает системную папку аддонов в проводнике.

---

## 📄 Лицензия
MIT License. Свободно для модификации и создания собственных дополнений.
