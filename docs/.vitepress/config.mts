import { defineConfig } from 'vitepress'

export default defineConfig({
  head: [['link', { rel: 'icon', href: 'favicon.ico' }]],
  lang: "en-US",
  title: "Nexus",
  description: "Nexus gives you ability to personalize your Minecraft server with useful features",
  
  lastUpdated: true,
  cleanUrls: true,
  
  markdown: {
    lineNumbers: true,
  },
  
  themeConfig: {
    logo: 'logo.png',
    editLink: {
      pattern: "https://github.com/BX-Team/Nexus/tree/master/docs/:path",
      text: "Edit this page on GitHub",
    },

    footer: {
      message: "Made by BX Team",
      copyright: "Copyright © 2023 BX Team",
    },
	
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Documentation', link: '/docs/getting-started/home' }
    ],
	
	search: {
      provider: "local",
    },

    sidebar: [
      {
        text: 'Getting Started',
        items: [
          { text: 'Introduction', link: '/docs/getting-started/home' },
          { text: 'Installing Nexus', link: '/docs/getting-started/installing' }
        ]
      },
      {
        text: 'Configuring',
        items: [
          { text: 'Configuring Nexus', link: '/docs/configuring/configuring' }
        ]
      },
      {
        text: 'Commands & Features',
        items: [
          { text: 'Commands', link: '/docs/commands-features/commands' },
          { text: 'Features', link: '/docs/commands-features/features' }
        ]
      }
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/BX-Team/Nexus' },
	    { icon: 'discord', link: 'https://discord.gg/p7cxhw7E2M' }
    ]
  }
})
