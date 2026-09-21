FROM node:24
WORKDIR /app
COPY . .
RUN npm install && npm run build
ENV NITRO_HOST=0.0.0.0
CMD ["node", ".output/server/index.mjs"]