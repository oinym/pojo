import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oinym.generated.GeneratedClassName;

public class JsonCompatibilityTest {

  @Test
  public void testJsonDeserialization() throws IOException {
    File jsonFile = new File("src/main/resources/sample.json");
    ObjectMapper objectMapper = new ObjectMapper();
    List<GeneratedClassName> root = objectMapper.readValue(jsonFile, new TypeReference<List<GeneratedClassName>>() {});

    // Assert that the deserialization was successful
    assertNotNull(root.get(0).getId());
    assertNotNull(root.get(0).getName());
  }
}
