FROM node:15-alpine

COPY . /src
RUN cd /src && npm install
CMD ["node", "/src/server.js"]
