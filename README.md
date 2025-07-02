<div align="center">

![Banner](https://raw.githubusercontent.com/BX-Team/Nexus/refs/heads/master/assets/readme-banner.png)
### Nexus
Powerful plugin that gives you ability to personalize your Minecraft server with useful features.

[![Available on Modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/plugin/nexuss)

[![Chat on Discord](https://raw.githubusercontent.com/vLuckyyy/badges/main//chat-with-us-on-discord.svg)](https://discord.gg/qNyybSSPm5)
[![Read the Docs](https://raw.githubusercontent.com/vLuckyyy/badges/main/read-the-documentation.svg)](https://bxteam.org/docs/nexus)
[![Available on BStats](https://raw.githubusercontent.com/vLuckyyy/badges/main/available-on-bstats.svg)](https://bstats.org/plugin/bukkit/Nexus%20Essentials/19684)
</div>

## ⚠️ Warning
Nexus is under heavy development right now. A lot of features are not fully ready yet. You can follow the development [here](https://github.com/orgs/BX-Team/projects/9).

Following our plans, the stable version is scheduled to be released in the **fall of 2025**.

## About
Nexus gives you ability to personalize your Minecraft server with useful commands. Our plugin offers a wide range of features, including:

- ⌨️ Over **50+** useful commands.
- 💬 Chat features, including:
  - Admin Chat
  - Chat on/off
  - Chat slow mode
  - Chat clear
  - Custom join, quit and death messages
  - Social commands (`/msg`, `/r`, `/ignore`, `/unignore` and `/socialspy`)
  - Help command for calling staff members (`/help`, `/report`)
- 💤 AFK System
- ❌ Feature that allows to bypass slots limit in the server (`nexus.fullserverbypass`)
- 🔨 Commands for opening utility GUIs (`/workbench`, `/anvil`, `/enderchest`, and more)
- ❤️ Player management commands (`/heal`, `/feed`, `/fly`, `/god`, and more)
- 🏓 Ping Command to check client-server connectivity
- 🔗 Server Links support, that was introduced in Minecraft 1.21
- 🏠 Home, Warp, Spawn and Jail system
- 👤 Player Information Command (`/whois`)
- 📄 PlaceholderAPI Support
- 🌐 Multi-language support and customizable messages
- 📇 Database Integration (SQLite, MariaDB, PostgreSQL)
- 🌈 [MiniMessage](https://docs.advntr.dev/minimessage/format.html) integration with [legacy color](https://minecraft.tools/en/color-code.php) processing (e.g., `&7`, `&e`)
- [...and much more!](https://bxteam.org/docs/nexus/reference/features)

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

## Contributing

Create a public fork of this repository, make your changes, and submit a pull request. We welcome contributions of all kinds, including code, documentation, and translations.
See [CONTRIBUTING.md](.github/CONTRIBUTING.md) for more details.

## Credits

During development of Nexus we use many other libraries as dependencies, including:

| Library                                                                   | Description                                            |
|---------------------------------------------------------------------------|--------------------------------------------------------|
| [Paper API](https://github.com/PaperMC/Paper)                             | Just a basic API for Minecraft plugin development      |
| [PaperLib](https://github.com/PaperMC/PaperLib)                           | Plugin library for interfacing into specific Paper API |
| [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)        | Plugin hook for creating placeholders                  |
| [bStats](https://github.com/Bastian/bStats)                               | Plugin metrics collection                              |
| [Guice](https://github.com/google/guice)                                  | Lightweight dependency injection framework             |
| [ClassGraph](https://github.com/classgraph/classgraph)                    | Java classpath scanner and module scanner              |
| [TriumphGUI](https://github.com/TriumphTeam/triumph-gui)                  | Simple inventory GUI library                           |
| [Google Guava](https://github.com/google/guava)                           | Core Java libraries by Google                          |
| [Gson](https://github.com/google/gson)                                    | JSON serialization/deserialization library             |
| [Lombok](https://github.com/projectlombok/lombok)                         | Java annotation processor to reduce boilerplate code   |
| [ORMLite](https://github.com/j256/ormlite-core)                           | Object Relational Mapping library                      |
| [HikariCP](https://github.com/brettwooldridge/HikariCP)                   | High-performance JDBC connection pool                  |
| [LiteCommands](https://github.com/Rollczi/LiteCommands)                   | Command framework for Minecraft servers                |
| [Adventure API](https://github.com/KyoriPowered/adventure)                | User interface library for Minecraft                   |
| [MiniMessage](https://github.com/KyoriPowered/adventure-text-minimessage) | Text formatting library for Adventure                  |
| [Okaeri Configs](https://github.com/OkaeriPoland/okaeri-configs)          | Configuration library with serialization support       |
| [Multification](https://github.com/EternalCodeTeam/multification)         | Notification system library                            |

Special thanks to the [EternalCore](https://github.com/EternalCodeTeam/EternalCore) project and their team for providing an excellent reference and inspiration for the development of our project.
Their approach to plugin architecture and features has been incredibly valuable in guiding our own development process.
