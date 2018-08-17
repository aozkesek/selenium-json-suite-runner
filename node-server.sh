java -Dselenium.LOGGER=./logs/node-server.log \
	-Dselenium.LOGGER.level=ALL \
	-jar ~/lib/selenium-server-standalone-3.13.0.jar \
	-role node \
	-nodeConfig	./node.json \
	
