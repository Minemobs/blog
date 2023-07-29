FROM node:lts as build

RUN mkdir -p /app /app
WORKDIR /app
COPY . .

RUN npm i
RUN npx astro build

ENV HOST=0.0.0.0
ENV PORT=3000

EXPOSE 3000
LABEL author="minemobs"

CMD node ./dist/server/entry.mjs