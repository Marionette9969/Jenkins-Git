FROM node:18-alpine

# Create app directory
WORKDIR /app

# Add a simple server
COPY server.js .

EXPOSE 3000
CMD ["node", "server.js"]
