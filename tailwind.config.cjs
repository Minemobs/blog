/** @type {import('tailwindcss').Config} */
module.exports = {
	content: ['./src/**/*.astro', './src/pages/blog/*.mdx'],
	theme: {
		extend: {},
	},
	plugins: [
		require('@tailwindcss/typography'),
	],
}
