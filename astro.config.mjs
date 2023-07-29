import { defineConfig } from 'astro/config';
import mdx from "@astrojs/mdx";
import tailwind from "@astrojs/tailwind";
import node from "@astrojs/node";
import { astroImageTools } from "astro-imagetools";
import prefetch from "@astrojs/prefetch";

import compress from "astro-compress";

// https://astro.build/config
export default defineConfig({
  integrations: [mdx(), tailwind(), prefetch(), astroImageTools, compress()],
  scopedStyleStrategy: 'class',
  vite: {
    assetsInclude: ['**/*.kt']
  },
  output: "server",
  adapter: node({
    mode: "standalone"
  })
});