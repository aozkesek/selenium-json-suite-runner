java -Dselenium.LOGGER=./logs/node-server.log \
	-Dselenium.LOGGER.level=ALL \
	-jar ~/bin/selenium-server-standalone-3.5.3.jar \
	-role node \
	-nodeConfig	./node.json \
	