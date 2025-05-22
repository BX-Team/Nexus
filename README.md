<div align="center">

![Banner](https://raw.githubusercontent.com/BX-Team/Nexus/refs/heads/master/assets/readme-banner.png)
### Nexus
Powerful plugin that gives you ability to personalize your Minecraft server with useful features.

[![Available on Modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/plugin/nexuss)

[![Chat on Discord](https://raw.githubusercontent.com/vLuckyyy/badges/main//chat-with-us-on-discord.svg)](https://discord.gg/qNyybSSPm5)
[![Read the Docs](https://raw.githubusercontent.com/vLuckyyy/badges/main/read-the-documentation.svg)](https://bxteam.org/docs/nexus)
[![Available on BStats](https://raw.githubusercontent.com/vLuckyyy/badges/main/available-on-bstats.svg)](https://bstats.org/plugin/bukkit/Nexus%20Essentials/19684)
</div>

## About
Nexus gives you ability to personalize your Minecraft server with useful commands. Our plugin offers a wide range of features, including:

- ⌨️ Over **50+** useful commands.
- 💬 Chat features, including:
  - Admin Chat
  - Chat on/off
  - Chat slow mode
  - Chat clear
  - Social commands (`/msg`, `/r`, `/ignore`, `/unignore` and `/socialspy`)
  - Help command for calling staff members (`/help`, `/report`)
- 💤 AFK System
- ❌ Feature that allows to bypass slots limit in the server (`nexus.fullserverbypass`)
- 🔨 Commands for opening utility GUIs (`/workbench`, `/anvil`, `/enderchest`, and more)
- ❤️ Player management commands (`/heal`, `/feed`, `/fly`, `/god`, and more)
- 🏓 Ping Command to check client-server connectivity
- 🏠 Home, Warp, Spawn and Jail system
- 👤 Player Information Command (`/whois`)
- 📄 PlaceholderAPI Support
- 🌐 Multi-language support and customizable messages
- 📇 Database Integration (SQLite, MariaDB, PostgreSQL)
- 🌈 [MiniMessage](https://docs.advntr.dev/minimessage/format.html) integration with [legacy color](https://minecraft.tools/en/color-code.php) processing (e.g., `&7`, `&e`)
- [...and much more!](https://docs.bxteam.org/documentation/nexus/reference/features)

## Installation

### Stable builds

You can download the latest stable builds from our [Modrinth page](https://modrinth.com/plugin/nexuss).

### Development builds

Get the latest development builds from our [GitHub Actions](https://github.com/BX-Team/Nexus/actions/workflows/build.yml).

## Building

To build Nexus, follow these steps (Make sure you have **JDK 17 or higher**):

```bash
./gradlew clean nexus-plugin:shadowJar
```

The compiled JAR file will be located in `nexus-plugin/build/libs`.
