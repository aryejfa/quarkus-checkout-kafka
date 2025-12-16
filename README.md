# quarkus-checkout-kafka

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode (on this laptop)

This project was tested on this machine using the system-installed Maven and a Java 17 target.

Preferred dev-mode command (uses system `mvn` because the Maven wrapper is not configured here):

```bash
mvn quarkus:dev
```

Notes:
- Dev mode enables Live Coding (hot-reload). Edit and save files under `src/main/java` or `src/main/resources` and Quarkus will reload automatically.
- The app listens on http://localhost:8080 and the Dev UI is available at http://localhost:8080/q/dev/ when dev mode runs.
- If you prefer the Maven Wrapper (`./mvnw`) we can repair the wrapper files; otherwise using the system `mvn` is the simplest option.

## Packaging and running the application

Package using system Maven:

```bash
mvn package -DskipTests
```

This produces the runnable layout under `target/quarkus-app/`.

Run the assembled app with:

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

To build an über-jar (fat jar):

```bash
mvn package -Dquarkus.package.jar.type=uber-jar -DskipTests
```

Run the über-jar with `java -jar target/*-runner.jar`.

## Creating a native executable

If you need a native executable you can use:

```bash
mvn package -Dnative
```

Or build the native image inside a container (no local GraalVM required):

```bash
mvn package -Dnative -Dquarkus.native.container-build=true
```

The produced native runner will be placed in `target/` (name varies by artifact).

See <https://quarkus.io/guides/maven-tooling> for details.

## Tests

Run unit tests:

```bash
mvn test
```

Integration tests in this project are configured to be skipped by default via the `skipITs` property in `pom.xml`.
To run integration tests, override that property:

```bash
mvn verify -DskipITs=false
```

## Provided Code

### REST

The example REST endpoint is implemented in `src/main/java/org/ejfa/GreetingResource.java` and exposes `GET /hello` which returns plain text. This is useful to verify dev-mode hot-reload.

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

---

If you want, I can also:
- Repair the Maven wrapper so `./mvnw` works, or
- Add a small `Makefile` or `scripts/` folder with shortcuts to run dev/test/package with the correct `JAVA_HOME`.


