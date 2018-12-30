SELENIUM_JAR=`ls ~/lib/selenium-server-standalone*.jar`
java -Dselenium.LOGGER=./logs/node-server.log \
	-Dselenium.LOGGER.level=ALL \
	-jar $SELENIUM_JAR \
	-role node \
	-nodeConfig	./node.json \
	
