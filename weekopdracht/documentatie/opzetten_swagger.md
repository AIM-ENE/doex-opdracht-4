# Opzetten swagger

[Gebruik de instructie van FSWD voor het opzetten van swagger](https://aim-cnp.github.io/fswd/docs/%F0%9F%8E%93%20Masterclasses/API-First%20Development%20and%20OpenAPI/1.7%20-%20Integrating%20Swagger%20with%20Spring%20Boot)

de API specificatie die is gegeven kan je [hier vinden](openapi.json)

## Extra uitdaging

Wanneer je een extra uitdaging zoekt kun je op basis van de API specificatie ook een gedeelte van de code genereren.

- Onderstaande maven dependency is nodig voor code generatie
```java
<dependency>
    <groupId>io.swagger.core.v3</groupId>
    <artifactId>swagger-annotations</artifactId>
    <version>2.2.20</version>
</dependency>
```

- Daarnaast is ook de code generator zelf vereist, voeg hiervoor deze plugin te aan de pom.xml
```java
<build>
    <plugins>
        <plugin>
            <groupId>org.openapitools</groupId>
            <artifactId>openapi-generator-maven-plugin</artifactId>
            <version>7.8.0</version>
            <executions>
                <execution>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                    <configuration>
                        <inputSpec>${project.basedir}/src/main/resources/static/bestelsysteem.json</inputSpec>
                        <generatorName>spring</generatorName>
                        <packageName>bestelsysteem.openapi</packageName>
                        <apiPackage>bestelsysteem.openapi.api</apiPackage>
                        <modelPackage>bestelsysteem.openapi.model</modelPackage>
                        <configOptions>
                            <openApiNullable>false</openApiNullable> <!--should add jackson nullable dependency if true-->
                            <useSpringBoot3>true</useSpringBoot3>
                            <hateoas>false</hateoas>
                            <useSwaggerUI>true</useSwaggerUI>
                            <useTags>true</useTags>
                            <interfaceOnly>true</interfaceOnly>
                            <useSpringController>true</useSpringController>
                            <useResponseEntity>false</useResponseEntity>
                            <library>spring-boot</library>
                        </configOptions>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

## Links:

- https://medium.com/xgeeks/api-first-using-openapi-and-spring-boot-2602c04bb0d3
- https://mydeveloperplanet.com/2022/02/08/generate-server-code-using-openapi-generator/
- https://medium.com/@bbakla/exploring-openapi-with-spring-boot-3-maven-and-gradle-usage-87c9bebf74c2
- https://openapi-generator.tech/docs/generators/java/