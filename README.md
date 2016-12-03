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


