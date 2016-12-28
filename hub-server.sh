java -Dselenium.LOGGER=./logs/hub-server.log \
	-Dselenium.LOGGER.level=ALL \
	-jar ~/bin/selenium-server-standalone-3.0.1.jar \
	-role hub \
	-hubConfig ./hub.json