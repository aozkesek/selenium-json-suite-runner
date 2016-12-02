# selenium-json-suite-runner
Runs Selenium suite &amp; tests from json formatted files

## Purpose 

This project gives ability to the user who do not know programming language  
automate test every web based user interfaces without coding and compiling  
java source code.

## Using 

User must know JSON file and format to write suite and test.  

User also should know Selenium Browser Automation.  Please refer this link  
[link][http://docs.seleniumhq.org] for more information.

### Suites

duckduckgo-suite.json
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

enter-world.json
---
{  
    "name": "duckduckgo test enter world",  
    "arguments": {  
    	"queryWord": "",  
    	"resultPerPage": "10"  
    },  
    "vars": {},  
    "commands": [  
        { "command": "isEnabled", "args": "${search_form_input_homepage}", "value": "${isSearchFormEnabled}" },  
        { "command": "isDisplayed", "args": "${search_form_input_homepage}", "value": "${isSearchFormDisplayed}" },      
        { "command": "sendKeys", "args": "${search_form_input_homepage}", "value": "${queryWord}" },  
        { "command": "submit", "args": "${search_form_input_homepage}" },  
        { "command": "assertEq", "args": "${search_form_input_homepage},search_form_input_homepage" },  
        { "command": "assertNeq", "args": "${search_form_input_homepage},id=search_form_input_homepage" },  
        { "command": "log", "args": "${search_form_input_homepage},id=search_form_input_homepage" },  
		{ "command": "log", "args": "${search_form_input_homepage},id=search_form_input_homepage", "value": "info" },  
		{ "command": "set", "args": "${search_form_input_homepage} is the value to set", "value": "${setvaluevariable}" }  
    ]  
}  

check-result.json
---
{  
    "name": "duckduckgo test check results",  
    "arguments": {  
    	"searchFormInput": ""  
    },  
    "vars": {},  
    "commands": [  
        { "command": "getAttribute", "args": "${searchFormInput},name", "value": "${searchInputName}" },  
        { "command": "assertNeq", "args": "${searchInputName},q" },  
        { "command": "clear", "args": "${searchFormInput}" },  
        { "command": "sendKeys", "args": "${searchFormInput}", "value": "merve ozkesek" },  
        { "command": "submit", "args": "${searchFormInput}" }  
    ]  
}  