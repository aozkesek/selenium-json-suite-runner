#selenium server command line arguments

##core-runner-configuration:
-htmlSuite
	browser string, base URL, test suite URL, results file

##grid-configuration:
-cleanUpCycle
	<Integer> in ms : specifies how often the hub will poll running proxies for timed-out (i.e. hung) threads. Must also specify \"timeout\" option"

-custom
	<String> : comma separated key=value pairs for custom grid extensions. NOT RECOMMENDED -- may be deprecated in a future revision. Example: -custom myParamA=Value1,myParamB=Value2",
 
-host
	<String> IP or hostname : usually determined automatically. Most commonly useful in exotic network configurations (e.g. network with VPN)
  
-maxSession
	<Integer> max number of tests that can run at the same time on the node, irrespective of the browser used
  
-servlet, -servlets
	<String> : list of extra servlets the grid (hub or node) will make available. Specify multiple on the command line: 
	-servlet tld.company.ServletA -servlet tld.company.ServletB. The servlet must exist in the path: /grid/admin/ServletA /grid/admin/ServletB
  
-withoutServlet, -withoutServlets
	<String> : list of default (hub or node) servlets to disable. Advanced use cases only. Not all default servlets can be disabled. Specify multiple on the command line: 
	-withoutServlet tld.company.ServletA -withoutServlet tld.company.ServletB"

##grid-hub-configuration:
-hubConfig
	"<String> filename: a JSON file (following grid2 format), which defines the hub properties

-matcher, -capabilityMatcher
	<String> class name : a class implementing the CapabilityMatcher interface. 
		Specifies the logic the hub will follow to define whether a request can be assigned to a node. 
		For example, if you want to have the matching process use regular expressions instead of exact match when specifying browser version. 
		ALL nodes of a grid ecosystem would then use the same capabilityMatcher, as defined here."

-newSessionWaitTimeout
	<Integer> in ms : The time after which a new test waiting for a node to become available will time out. 
		When that happens, the test will throw an exception before attempting to start a browser. An unspecified, zero, or negative value means wait indefinitely."

-prioritizer
	<String> class name : a class implementing the Prioritizer interface. 
		Specify a custom Prioritizer if you want to sort the order in which new session requests are processed when there is a queue. Default to null ( no priority = FIFO )

-throwOnCapabilityNotPresent
	<Boolean> true or false : 
		If true, the hub will reject all test requests if no compatible proxy is currently registered. If set to false, the request will queue until a node supporting the capability is registered with the grid.

##grid-node-configuration:
-nodeConfig
	<String> filename : JSON configuration file for the node. Overrides default values

-hubHost
	<String> IP or hostname : the host address of the hub we're attempting to register with. If -hub is specified the -hubHost is determined from it.

-hubPort
	<Integer> : the port of the hub we're attempting to register with. If -hub is specified the -hubPort is determined from it.

-id
	description = "<String> : optional unique identifier for the node. Defaults to the url of the remoteHost, when not specified.

-capabilities, -browser
	description = "<String> : comma separated Capability values. 
	Example: -capabilities browserName=firefox,platform=linux -capabilities browserName=chrome,platform=linux

-downPollingLimit
	description = "<Integer> : node is marked as \"down\" if the node hasn't responded after the number of checks specified in [downPollingLimit].

-hub
	description = "<String> : the url that will be used to post the registration request. This option takes precedence over -hubHost and -hubPort options.

-nodePolling
	<Integer> in ms : specifies how often the hub will poll to see if the node is still responding.

-nodeStatusCheckTimeout
	<Integer> in ms : connection/socket timeout, used for node \"nodePolling\" check.

-proxy
	<String> : the class used to represent the node proxy. Default is [org.openqa.grid.selenium.proxy.DefaultRemoteProxy].

-register
	if specified, node will attempt to re-register itself automatically with its known grid hub if the hub becomes unavailable.

-registerCycle
	<Integer> in ms : specifies how often the node will try to register itself again. Allows administrator to restart the hub without restarting (or risk orphaning) registered nodes. 
	Must be specified with the \"-register\" option.

-unregisterIfStillDownAfter
	<Integer> in ms : if the node remains down for more than [unregisterIfStillDownAfter] ms, it will stop attempting to re-register from the hub.

##standalone-configuration:
-avoidProxy
	DO NOT USE: Hack to allow selenium 3.0 server run in SauceLabs

-browserSideLog
	DO NOT USE: Provided for compatibility with 2.0

-captureLogsOnQuit
	DO NOT USE: Provided for compatibility with 2.0"

--help, -help, -h
	Displays this help

-browserTimeout
	<Integer> in seconds : number of seconds a browser session is allowed to hang while a WebDriver command is running (example: driver.get(url)). 
	If the timeout is reached while a WebDriver command is still processing, the session will quit. Minimum value is 60. An unspecified, zero, or negative value means wait indefinitely.

-debug
	<Boolean> : enables LogLevel.FINE."

-jettyThreads, -jettyMaxThreads
	<Integer> : max number of threads for Jetty. An unspecified, zero, or negative value means the Jetty default value (200) will be used.

-log
	<String> filename : the filename to use for logging. If omitted, will log to STDOUT

-port
	<Integer> : the port number the server will use.

-role
	<String> options are [hub], [node], or [standalone]

-timeout, -sessionTimeout
	<Integer> in seconds : Specifies the timeout before the server automatically kills a session that hasn't had any activity in the last X seconds. 
	The test slot will then be released for another test to use. This is typically used to take care of client crashes. For grid hub/node roles, cleanUpCycle must also be set.
