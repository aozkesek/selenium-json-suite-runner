java -Dselenium.LOGGER=./logs/hub-server.log \
	-Dselenium.LOGGER.level=ALL \
	-jar ~/lib/selenium-server-standalone-3.13.0.jar \
	-role hub \
	-hubConfig ./hub.json
