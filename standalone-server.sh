SELENIUM_JAR=`ls ~/lib/selenium-server-standalone*.jar`
java -Dselenium.LOGGER=./logs/selenium-server.log \
	-Dselenium.LOGGER.level=ALL \
	-jar $SELENIUM_JAR \
	-role standalone \
