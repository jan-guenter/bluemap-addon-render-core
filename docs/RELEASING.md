# Releasing

Releases require a clean reviewed commit and an annotated tag named exactly
`v<module_version>`.

1. Run `clean check verifyPublication` with Gradle 9.4.0 and 9.6.1 on Java 21
   against the exact clean BlueMap 5.23 feature checkout.
2. Build with Gradle 9.6.1 twice and compare the production JAR, sources JAR,
   POM, and module metadata byte for byte.
3. Inspect both archives and confirm the origin and archive-boundary checks.
4. Confirm publication metadata contains no dependency.
5. Merge the reviewed version commit.
6. Create and push a signed annotated `v<module_version>` tag at that commit.
7. Let the release workflow build, compare, attest, publish to Maven, verify
   downloaded assets, and publish the draft prerelease.

The workflow can resume only against the same immutable annotated tag. Before
publishing Maven coordinates, it downloads and byte-compares any existing
binary JAR, sources JAR, POM, and Gradle module metadata. An exact existing
publication is reused; an absent publication is created; partial or differing
coordinates stop the release. A successful module release does not authorize
a consumer update or deployment.
