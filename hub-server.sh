SELENIUM_JAR=`ls $HOME/.local/lib/selenium-server-standalone*.jar`
java -Dselenium.LOGGER=./logs/hub-server.log \
	-Dselenium.LOGGER.level=ALL \
	-jar $SELENIUM_JAR \
	-role hub \
	-hubConfig ./hub.json
