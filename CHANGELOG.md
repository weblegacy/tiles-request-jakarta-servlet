# Changes

## 1.0.7 / YYYY-MM-DD

* Change Artifact-Id to `tiles-request-jakarta-servlet` and version to 1.0.0-SNAPSHOT
* Add `maven-release-plugin`
* Add `maven-install-plugin`
* Add `maven-deploy-plugin`
* Add `maven-clean-plugin`
* Add `maven-assembly-plugin`
* Add `maven-antrun-plugin`
* Resolve `PMD` and `Checkstyle` warnings
* Resolve all JavaDoc-warnings
* Remove not needed aggregate-reports
* Set better JavaDoc-options
* Add Homepage-URL
* Enforce minimum MAVEN-Version from 3.6.3 to 3.3.9
* Define own `slf4j`-version
* Reformat JavaDocs
* Resolve JDK-warnings
* Add site-documentation
* Add `maven-resources-plugin`
* Enforce minimum JDK from 8 to 9
* Correct `module-info.java`
* Add multi-release-compiling for JDK8 and JDK9
* Remove `Automatic-Module-Name` because existing `module-info.java`
* Remove `japicmp-maven-plugin`
* Add `maven-site-plugin`
* Bump `jacoco-maven-plugin` from 0.8.10 to 0.8.11
* Bump `spotbugs` from 4.7.3 to 4.8.0
* Bump `spotbugs-maven-plugin` from 4.7.3.4 to 4.7.3.6
* Bump `maven-surefire-plugin` 3.0.0 and `maven-surefire-report-plugin` 3.1.2 to 3.2.1
* Bump `maven-project-info-reports-plugin` from 3.4.3 to 3.4.5
* Bump `maven-pmd-plugin` from 3.20.0 to 3.21.2
* Bump `maven-javadoc-plugin` from 3.5.0 to 3.6.0
* Bump `maven-enforcer-plugin` from 3.3.0 to 3.4.1
* Bump `maven-dependency-plugin` from 3.5.0 to 3.6.1
* Bump `checkstyle` from 10.12.3 to 10.12.4
* Bump `maven-checkstyle-plugin` from 3.2.2 to 3.3.1
* Bump `easymock` from 5.1.0 to 5.2.0
* Bump `junit` from 4.7 to 4.13.2
* Add more informations to `pom.xml`
* Adapt XML header in `checkstyle.xml` and `pom.xml`
* Replace Copyrights in sources to current Apache-2.0-version
* Add `NotAServletEnvironmentException` to replace dependency `tiles-request-servlet` with `tiles-request-api`
* Replace tabs with spaces and make right-trim
* Rename packages from `io.github.weblegacy.tiles.request.servlet` to `org.apache.tiles.request.jakarta.servlet`
* Change `license-header.txt` and `NOTICE.txt`
* delete `bin`-directory
* Change `.gitignore`
* Add `README.md`
* Add `CHANGELOG.md`
* added some documentation and additional checkstyle fixes
* some checkstyle fixes
* added analysis maven dependencies
* Initial commit