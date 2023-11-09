# Tiles-Request-Jakarta-Servlet

Copy of [Apache Tiles Request Servlet](https://github.com/apache/tiles-request/tree/trunk/tiles-request-servlet), adapted for jakarata-servlet (EE9+) and extends with JPMS.

Full [CHANGELOG](CHANGELOG.md)

For documentation see [https://weblegacy.github.io/tiles-request-jakarta-servlet](https://weblegacy.github.io/tiles-request-jakarta-servlet).

## Versions-Overview

| Version                                                                                 | JEE-Version  | Java-Version | Servlet | JSP | EL  | JSF | JSTL |
|----------------------------------------------------------------------------------------:|-------------:|-------------:|--------:|----:|----:|----:|-----:|
| [1.0.0](https://github.com/weblegacy/tiles-request-jakarta-servlet/releases/tag/v1.0.0) | Jakarta EE 9 |            8 |     5.0 | 3.0 | 4.0 | 3.0 |  2.0 |

## Building Tiles-Request-Jakarta-Servlet

### Prerequisites

* Apache Maven 3.8.1\+
* JDK 9\+

### MAVEN-Profiles

* **release** - Signs all of the project's attached artifacts with GnuPG

### Building-Steps

1. Clean full project  
   `mvn clean`
2. Build and test project
   * with tests  
     `mvn verify`
   * to skip tests  
     add `-DskipTests` for example `mvn  -DskipTests verify`
3. Generate site-documentation  
   `mvn site`  
4. Publish site-documentation  
   `mvn clean site-deploy`
5. Generate Assemblies  
   `mvn package`
8. Deploy all artifacts to `Central-Repo`  
   * `mvn deploy` for SNAPSHOTs
   * `mvn -Prelease clean deploy` for releases

### Support runs

* Set version number  
  `mvn versions:set -DnewVersion=...`
* Dependency Report  
  `mvn versions:display-dependency-updates versions:display-plugin-updates versions:display-property-updates`
