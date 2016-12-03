# selenium-json-suite-runner
Runs Selenium suite &amp; tests from json formatted files

## Purpose 

This project gives ability to the user who do not know programming language  
to automate test every web based user interfaces without coding and compiling  
java source code.

## Using 

User must know JSON file and format to write suite and test.  

User also should know Selenium Browser Automation.  Please refer this link  
[SeleniumHQ](http://docs.seleniumhq.org) for more information.

### Objects

Holds predefined variables can be used within the suite and test.  

#### duckduckgo-objects.json
---
{  
	"includes": [  
	],  
    "objects": {  
	    "query_string": "ahmet ozkesek",  
	    "search_form_input_homepage": "id=search_form_input_homepage",  
	    "search_form_input": "id=search_form_input"   
    }  
}  
    

### Suites

Contains a group of test can be used to test a complex business flow.  

#### duckduckgo-suite.json
---
{  
    "name": "duckduckgo suit",  
    "object_repository": "duckduckgo-or/duckduckgo-objects.json",  
    "test_path": "duckduckgo-tests/",  
    "test_url": "https://duckduckgo.com/",  
    "tests": [  
        {  
            "file_name": "enter-world.json",  
            "arguments": {  
                "queryWord": "${query_string}",  
                "abcdef": ""  
            }  
        },  
        {  
            "file_name": "check-results.json",  
            "arguments": {  
            	"searchFormInput": "${search_form_input}"  
            }  
        }    
    ]    
}  

### Tests

Contains a group of commands can be used to test a simple business flow.  

#### enter-world.json
---

{  
    "name": "duckduckgo test enter world",  
    "arguments": {  
    	"queryWordEnter": "",  
    	"resultPerPage": "10"  
    },  
    "vars": {},  
    "commands": [  
        { "command": "isEnabled", "args": "${search_form_input_homepage}", "value": "${isSearchFormEnabled}" },  
        { "command": "isDisplayed", "args": "${search_form_input_homepage}", "value": "${isSearchFormDisplayed}" },      
        { "command": "sendKeys", "args": "${search_form_input_homepage}", "value": "${queryWordEnter}" },  
		{ "command": "submit", "args": "${search_form_input_homepage}" },  
        { "command": "sleep", "args": "3000" },  
		{ "command": "runTest", "args": "../commons/check-results.json", "value": "searchFormInputCheck:=${search_form_input}" }  
    ]  
}    

#### check-result.json
---

{  
    "name": "duckduckgo common test check results",  
    "arguments": {  
    	"searchFormInputCheck": ""  
    },  
    "vars": {},  
    "commands": [  
        { "command": "assertEq", "args": "${search_form_input_homepage},search_form_input_homepage" },  
        { "command": "log", "args": "${search_form_input_homepage},id=search_form_input_homepage" },  
		{ "command": "log", "args": "${search_form_input_homepage},id=search_form_input_homepage", "value": "info" },  
		{ "command": "set", "args": "${search_form_input_homepage} is the value to set", "value": "${setvaluevariable}" },  
		{ "command": "getAttribute", "args": "${searchFormInputCheck},name", "value": "${searchInputName}" },  
        { "command": "assertNeq", "args": "${searchInputName},q" }  
    ]  
}  

#### world-end.json
---

{  
    "name": "duckduckgo test world end ",  
    "arguments": {  
    	"queryWordEnd": "",  
    	"resultPerPage": "10"  
    },  
    "vars": {},  
    "commands": [  
        { "command": "isEnabled", "args": "${search_form_input}", "value": "${isSearchFormEnabled}" },  
        { "command": "isDisplayed", "args": "${search_form_input}", "value": "${isSearchFormDisplayed}" },      
        { "command": "clear", "args": "${search_form_input}" },  
        { "command": "sendKeys", "args": "${search_form_input}", "value": "${queryWordEnd}" },  
        { "command": "submit", "args": "${search_form_input}" },  
        { "command": "sleep", "args": "3000" },  
 		{ "command": "runTest", "args": "../commons/check-results.json", "value": "searchFormInputCheck:=${search_form_input}" }  
    ]  
}  

### Commands

As of version ?.?.? the available commands are;

* assertEq: asserts when two expressions are equal and fails the test.    
args: &lt;expr1&gt;,&lt;expr2&gt;  
* assertNeq: asserts when two expressions are not equal and fails the test.    
args: &lt;expr1&gt;,&lt;expr2&gt;  
* clear: clears the value of given web element.      
args: &lt;web-element-descriptor&gt;    
* click: clicks given web element.        
args: &lt;web-element-descriptor&gt;  
* getAttribute: gets the attribute of given web element.      
args: &lt;web-element-descriptor&gt;,&lt;attribute-name&gt;  
value: &lt;variable-name&gt;  
* getCssValue: gets the css value of given web element.      
args: &lt;web-element-descriptor&gt;,&lt;css-name&gt;  
value: &lt;variable-name&gt;  
* getText: gets the text of given web element.      
args: &lt;web-element-descriptor&gt;    
value: &lt;variable-name&gt;  
* isDisplayed: return true if given web element is displayed.      
args: &lt;web-element-descriptor&gt;  
value: &lt;variable-name&gt;  
* isEnabled: return true if given web element is enabled.      
args: &lt;web-element-descriptor&gt;  
value: &lt;variable-name&gt;  
* isSelected: return true if given web element is selected.      
args: &lt;web-element-descriptor&gt;  
value: &lt;variable-name&gt;  
* log: logs the given expression.  Log level is info or debug.      
args: &lt;expr&gt;  
value: info | debug  
* runTest: runs another test.      
args: &lt;full-test-path-name&gt;  
value: arguments key value pair in format k:=v  
* select: select .      
args: &lt;select-command&gt;  
value:   
* sendKeys: sends  .      
args: &lt;web-element-name&gt;  
value: &lt;expr-keys&gt;  
* set: stores given expression value into the variable  .      
args: &lt;expr&gt;  
value: &lt;variable-name&gt;  
* sleep: sleeps for a while in given ms  .      
args: &lt;time-in-milisec&gt;  
* submit: submits form that held the given web element  .      
args: &lt;web-element-name&gt;  
 


