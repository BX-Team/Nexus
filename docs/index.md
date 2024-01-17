---
layout: home

hero:
  name: "Nexus"
  tagline: Nexus gives you ability to personalize your Minecraft server with useful features
  image:
    src: logo.png
    alt: Nexus
  actions:
    - theme: brand
      text: Download
      link: "https://modrinth.com/mod/nexuss/versions"
    - theme: alt
      text: Documentation
      link: /docs/getting-started/home

features:
  - title: Essential commands
    details: Nexus adds over 30 useful commands for your server.
  - title: MySQL and SQLite database (WIP)
    details: Support for MySQL and SQLite databases where you can store information about players.
  - title: Sign and Book formatting
    details: Allows you to format signs and books with color codes.
  - title: Customizable join and leave messages
    details: Customize join and leave messages.
  - title: Glass and door knock
    details: Adds ability to knock on glass and doors.
  - title: Full server bypass
    details: Allows you to join full server.
  - title: Warp system
    details: Adds warp system to your server.
  - title: Multi-language support
    details: Supports multiple languages that can be easily added.
---

<style>
:root {
  --vp-home-hero-name-color: transparent;
  --vp-home-hero-name-background: -webkit-linear-gradient(120deg, #ffa38a 30%, #310085);

  --vp-home-hero-image-background-image: linear-gradient(-45deg, #ffa38a 50%, #310085 50%);
  --vp-home-hero-image-filter: blur(40px);
}

@media (min-width: 640px) {
  :root {
    --vp-home-hero-image-filter: blur(56px);
  }
}

@media (min-width: 960px) {
  :root {
    --vp-home-hero-image-filter: blur(72px);
  }
}
</style>
