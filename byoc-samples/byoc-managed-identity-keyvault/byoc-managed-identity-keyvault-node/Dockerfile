FROM node:15-alpine
ENV AZURE_KEYVAULT_URI https://<your-keyvault-name>.vault.azure.net/

COPY . /src
RUN cd /src && npm install
CMD ["node", "/src/server.js"]
