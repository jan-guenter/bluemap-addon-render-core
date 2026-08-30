# Changelog

## 0.1.0-alpha.2

- Move the production API to `adapter.bluemap523`.
- Make the exact BlueMap 5.23 feature-backport commit the sole compile and
  test target.
- Preserve the transformed-face lighting behavior and frozen-origin checks.

## 0.1.0-alpha.1

- Extract the exact seven-copy transformed-face lighting sampler into a
  consumer-neutral public package.
- Add frozen-origin transformation checks and focused identity, transform,
  sunlight, block-light, and emission-floor tests.
- Add exact archive, dependency-free publication, and dual-Gradle gates.
