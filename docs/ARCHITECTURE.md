# Architecture

## Packaging

Each BlueMap add-on compiles the shared Java source into its own production
JAR. Consumers pin this repository as source, normally through a Git
submodule. The standalone JAR, sources JAR, POM, and module metadata exist for
review and distribution; server administrators do not install the JAR.

## Version-specific lighting contract

`FaceLighting.sample` accepts BlueMap 5.22 `BlockNeighborhood`, `Direction`,
and `Variant` objects plus an emission floor. It transforms the face normal
through the variant matrix, rounds the resulting axial offset, samples that
neighbor, and returns immutable `sunlight` and `blocklight` maxima.

The public API is deliberately under `adapter.bluemap522`. These are internal
BlueMap core types rather than a stable public API, so a later BlueMap line
gets another adapter and compatibility gate instead of silently widening this
one.

## Boundaries

The module contains no block registration, add-on descriptor, service,
resource lookup, candidate-mod dependency, Minecraft or NeoForge linkage,
mesh emission, cache, diagnostics, or mutable singleton. Consumer-specific
rendering and safe stock fallback remain in each add-on.

The nested `Sample` record is immutable and JDK-only after construction. No
installed shared runtime is created; each consumer owns one class identity in
its own BlueMap add-on classloader.

## Initial promotion decision

Version 0.1 intentionally contains one helper API rather than padding the
module with weaker candidates. This is the explicitly prioritized render-core
repository, and the API is proven identical across seven current consumers,
removing 258 repeated source lines. Other small cohorts stay local until they
independently pass the same promotion gate.
