SELENIUM_JAR=`ls ~/lib/selenium-server-standalone*.jar`
java -Dselenium.LOGGER=./logs/hub-server.log \
	-Dselenium.LOGGER.level=ALL \
	-jar $SELENIUM_JAR \
	-role hub \
	-hubConfig ./hub.json
