import { defineConfig } from 'astro/config';
import mdx from "@astrojs/mdx";
import tailwind from "@astrojs/tailwind";
import node from "@astrojs/node";
import prefetch from "@astrojs/prefetch";
import compress from "astro-compress";

import image from "@astrojs/image";

// https://astro.build/config
export default defineConfig({
  integrations: [tailwind(), image(), mdx(), prefetch(), compress()],
  scopedStyleStrategy: 'class',
  vite: {
    assetsInclude: ['**/*.kt']
  },
  output: "server",
  adapter: node({
    mode: "standalone"
  })
});