// package com.oinym;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.jsonschema2pojo.*;
// import org.jsonschema2pojo.rules.RuleFactory;

// import com.sun.codemodel.JCodeModel;

// import java.io.File;
// import java.io.IOException;
// import java.net.URL;

// public class Main {

//     public static void main(String[] args) {
//         try {
//             // Specify the input JSON schema file and output directory for generated classes
//             File inputJson = new File("src/main/resources/person.json");
//             File outputJavaClasses = new File("src/main/java");
//             if (!outputJavaClasses.exists()) {
//                 boolean created = outputJavaClasses.mkdirs();
//                 if (created) {
//                     System.out.println("Output directory created: " + outputJavaClasses.getAbsolutePath());
//                 }
//             }

//             // Ensure the input JSON schema file exists
//             if (!inputJson.exists()) {
//                 throw new IOException("JSON file not found at: " + inputJson.getAbsolutePath());
//             }

//             // Initialize the CodeModel for class generation
//             JCodeModel codeModel = new JCodeModel();
//             URL source = inputJson.toURI().toURL();

//             // Configure the SchemaMapper
//             DefaultGenerationConfig config = new DefaultGenerationConfig() {
//                 @Override
//                 public boolean isGenerateBuilders() {
//                     return true; // Enable builder methods for generated classes
//                 }

//                 @Override
//                 public boolean isIncludeDynamicBuilders() {
//                     return false; // Exclude dynamic builder methods
//                 }
//             };

//             // Provide a valid Annotator (Jackson2Annotator in this case)
//             SchemaMapper mapper = new SchemaMapper(
//                     new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()),
//                     new SchemaGenerator());

//             // Generate Java classes from the JSON file
//             System.out.println("Generating classes from: " + inputJson.getAbsolutePath());
//             mapper.generate(codeModel, "GeneratedClassName", "output.com.example.generated", source);

//             // Write the generated Java files to the output directory
//             System.out.println("Writing classes to: " + outputJavaClasses.getAbsolutePath());
//             codeModel.build(outputJavaClasses);
//             System.out.println("Java classes generation completed!");
//             System.out.println("Generated classes count: " + codeModel.countArtifacts());
//         } catch (IOException e) {
//             e.printStackTrace();
//             System.err.println("Failed to generate Java classes: " + e.getMessage());
//         }
//     }
// }

package com.oinym;

import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        try {
            File inputJson = new File("src/main/resources/people.json");
            File outputJavaClasses = new File("src/main/java");
            if (!outputJavaClasses.exists()) {
                boolean created = outputJavaClasses.mkdirs();
                if (created) {
                    System.out.println("Output directory created: " + outputJavaClasses.getAbsolutePath());
                }
            }

            if (!inputJson.exists()) {
                throw new IOException("JSON file not found at: " + inputJson.getAbsolutePath());
            }

            JCodeModel codeModel = new JCodeModel();
            URL source = inputJson.toURI().toURL();

            DefaultGenerationConfig config = new DefaultGenerationConfig() {
                @Override
                public SourceType getSourceType() {
                    return SourceType.JSON; // Allow plain JSON input
                }

                @Override
                public boolean isGenerateBuilders() {
                    return true;
                }
                
                @Override
                public boolean isUseTitleAsClassname() {
                    return true; // Use "title" field in schema as the class name if available
                }

                
            };

            SchemaMapper mapper = new SchemaMapper(
                    new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()),
                    new SchemaGenerator());

            System.out.println("Generating classes from: " + inputJson.getAbsolutePath());
            mapper.generate(codeModel, "GeneratedClassName", "com.oinym.generated", source);

            System.out.println("Writing classes to: " + outputJavaClasses.getAbsolutePath());
            codeModel.build(outputJavaClasses);
            System.out.println("Java classes generation completed!");
            System.out.println("Generated classes count: " + codeModel.countArtifacts());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to generate Java classes: " + e.getMessage());
        }
    }
}
