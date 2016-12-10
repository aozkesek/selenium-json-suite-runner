java -Dselenium.LOGGER=./logs/node-server.log \
	-Dselenium.LOGGER.level=ALL \
	-jar ~/bin/selenium-server-standalone-3.0.1.jar \
	-role node \
	-hubHost localhost \
	-id node-server-1 \
	-capabilities browserName=firefox,platform=mac \
	