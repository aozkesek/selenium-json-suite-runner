import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;

import org.ao.suite.model.ObjectModel;
import org.ao.suite.model.SuiteModel;
import org.ao.suite.model.SuiteTestModel;
import org.ao.suite.test.model.TestModel;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YAMLScriptTests {
	
	@Test
	public void ValidateScripts( ) {
		ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
		
		try {
			SuiteModel suite = yamlMapper.readValue(new File("suites/duckduckgo-suite.yaml"), SuiteModel.class);
			System.out.println(suite);
			assertNotEquals(suite, null);
			
			ObjectModel object = yamlMapper.readValue(new File("objects/duckduckgo-or/duckduckgo-objects.yaml"), ObjectModel.class);
			System.out.println(object);
			assertNotEquals(object, null);
			
			String path = "tests/".concat(suite.getTestPath());
			
			for (SuiteTestModel suiteTest : suite.getTests()) {
				
				System.out.println(path.concat(suiteTest.getFileName()));
								
				TestModel test = yamlMapper.readValue(new File(path.concat(suiteTest.getFileName())), TestModel.class);
				System.out.println(test);
				assertNotEquals(test, null);
				
			}	
			
			TestModel test = yamlMapper.readValue(new File(path.concat("commons/check-results.yaml")), TestModel.class);
			System.out.println(test);
			assertNotEquals(test, null);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
