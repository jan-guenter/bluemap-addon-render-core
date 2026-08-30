# Agent guide for BlueMap Add-on Render Core

Read this file, `README.md`, `docs/ARCHITECTURE.md`, and
`provenance/origins.json` before changing production code.

## Scope

Version `0.1.0-alpha.1` contains only the exact seven-copy BlueMap 5.22
`FaceLighting` API in
`io.github.janguenter.bluemap.addon.render.core.adapter.bluemap522`. It samples
the transformed face neighbor and returns the maximum sunlight and block-light
levels, with the supplied emission as a floor.

Do not add mesh emission, resources, profiles, activation, registration,
entrypoints, mod behavior, installed-library behavior, or helpers that have
not passed the portfolio's multi-consumer promotion gate.

Consumers compile this repository's production source into their own add-on
JAR. They do not install or load the standalone module JAR on a server.

## Origin contract

The canonical MIT source first appears in BlueMap Sophisticated Add-on commit
`8cdb778db3c123aec472404fdafecde28928e4b7`. The frozen release snapshot is
`a75b1d82c3987fa9360a1e8a5910eedf90aca7cb`. The production source changes
only the package and the visibility of the class, method, and nested record.
`verifyOriginSources` checks the frozen bytes and every declared
transformation.

Do not change behavior during extraction. A later behavior change needs a new
version, focused tests, consumer review, and combined runtime evidence.

## Required gates

Use a clean recursive checkout of the exact BlueMap ABI and the shared lock:

```bash
flock /tmp/bluemap-gradle.lock \
  gradle-9.4.0 --no-daemon \
  -PbluemapSourcePath=/path/to/exact/bluemap clean check verifyPublication
flock /tmp/bluemap-gradle.lock \
  gradle-9.6.1 --no-daemon \
  -PbluemapSourcePath=/path/to/exact/bluemap clean check verifyPublication
```

Before release, build the four publication files twice with Gradle 9.6.1 and
compare every byte. Inspect both JARs and run
`actionlint .github/workflows/*.yml` after workflow changes.

Never commit build output, credentials, generated release files, consumer
JARs, or pack evidence. A release needs a reviewed commit and an annotated
`v<module_version>` tag.
