SELENIUM_JAR=`ls $HOME/.local/lib/selenium-server-*.jar`

java -Dselenium.LOGGER=./logs/selenium-server.log \
	-Dselenium.LOGGER.level=ALL \
	-jar $SELENIUM_JAR standalone

