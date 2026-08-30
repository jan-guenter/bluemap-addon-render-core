# Agent guide for BlueMap Add-on Render Core

Read this file, `README.md`, `docs/ARCHITECTURE.md`, and
`provenance/origins.json` before changing production code.

## Scope

Version `0.1.0-alpha.2` contains only the exact seven-copy BlueMap 5.23
`FaceLighting` API in
`io.github.janguenter.bluemap.addon.render.core.adapter.bluemap523`. It samples
the transformed face neighbor and returns the maximum sunlight and block-light
levels, with the supplied emission as a floor.

The sole BlueMap target is feature-backport commit
`7e07f4e74ec1e92a6ead9aa1e66054af3e133aac` with BlueMapAPI commit
`285c9a60eff3ac2b0cab308ce1058d1565be0971`.

Do not add mesh emission, resources, profiles, activation, registration,
entrypoints, mod behavior, installed-library behavior, or helpers that have
not passed the portfolio's multi-consumer promotion gate.

Consumers compile this repository's production source into their own add-on
JAR. They do not install or load the standalone module JAR on a server.

## Origin contract

The canonical MIT source first appears in BlueMap Sophisticated Add-on commit
`8cdb778db3c123aec472404fdafecde28928e4b7`. The frozen release snapshot is
`a75b1d82c3987fa9360a1e8a5910eedf90aca7cb`. The production source changes
only the historical package and the visibility of the class, method, and
nested record. The oracle retains its original package but is never published.
`verifyOriginSources` checks the frozen bytes and every declared transformation.

Do not change behavior during extraction. A later behavior change needs a new
version, focused tests, consumer review, and combined runtime evidence.

## Required gates

Use a clean recursive checkout of the exact BlueMap ABI and the shared lock:

```bash
flock /tmp/bluemap-gradle.lock \
  gradle-9.4.0 --no-daemon \
  -PbluemapSourcePath=/path/to/exact/bluemap-5.23 clean check verifyPublication
flock /tmp/bluemap-gradle.lock \
  gradle-9.6.1 --no-daemon \
  -PbluemapSourcePath=/path/to/exact/bluemap-5.23 clean check verifyPublication
```

Before release, build the four publication files twice with Gradle 9.6.1 and
compare every byte. Inspect both JARs and run
`actionlint .github/workflows/*.yml` after workflow changes.

Never commit build output, credentials, generated release files, consumer
JARs, or pack evidence. A release needs a reviewed commit and a signed
annotated `v<module_version>` tag.
