# BlueMap Add-on Render Core

This Java 21 source module begins the portfolio's narrow shared rendering
core with one proven API: transformed-face light sampling for BlueMap 5.22.
`FaceLighting.sample` rotates the requested face through the model variant,
samples that adjacent block, and returns the maximum sunlight and block light
from the host and exposed neighbor while respecting the model's emission.

The API is in
`io.github.janguenter.bluemap.addon.render.core.adapter.bluemap522`. It is
exactly the behavior previously repeated in Chipped, CobbleFurnies,
Integrated Dynamics, LaserIO, Pipez, Powah, and Sophisticated.

## Consumer model

BlueMap gives add-ons separate classloaders and no dependable installed
library version contract. Pin this repository at an exact commit and compile
its `src/main/java` directory into each consumer's production JAR. Do not copy
`bluemap-addon-render-core-*.jar` to the BlueMap packs directory and do not
nest it inside a consumer.

For a consumer that pins the module at `modules/bluemap-addon-render-core`:

```groovy
sourceSets {
    main.java.srcDir 'modules/bluemap-addon-render-core/src/main/java'
}
```

The consumer must verify its gitlink and checkout before compilation. Its JAR
audit must admit exactly one copy of `FaceLighting` and
`FaceLighting$Sample`, reject the legacy local class, and reject a nested
module JAR.

## Exact ABI

The version-specific adapter is compiled against the exact BlueMap 5.22
backport commit `9be321df995a1103808621d529eb72773e719d4d` and BlueMapAPI
commit `285c9a60eff3ac2b0cab308ce1058d1565be0971`. The Maven POM and
Gradle module metadata intentionally publish no production dependency because
the standalone JAR is a review artifact, not an installed library.

The same focused suite also compiles against the current 5.23 backport feature
commit `7e07f4e74ec1e92a6ead9aa1e66054af3e133aac`. That is a bounded
compatibility check, not a claim that other BlueMap internal APIs are stable.

## Build

Use Java 21, Gradle 9.4.0 or 9.6.1, and a clean recursive checkout of that
exact BlueMap backport:

```bash
gradle --no-daemon \
  -PbluemapSourcePath=/path/to/bluemap clean check verifyPublication
```

`check` runs origin transformation, API, lighting, Checkstyle, and exact
archive gates. `verifyPublication` checks the dependency-free publication
identity. Release builds use Gradle 9.6.1 because its version appears in
Gradle module metadata.

## Deliberate exclusions

This release contains no mesh emission, geometry records, transforms beyond
the exact BlueMap variant operation, culling, materials, resources, routes,
profiles, activation, fallback policy, registration, mutable state, or
candidate-mod behavior. Future additions require another proven neutral
multi-consumer contract.
