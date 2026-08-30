# Third-party material

The production source comes from the owner's MIT BlueMap Sophisticated
Add-on. The module contains no candidate-mod source, class, model, texture,
translation, capture, or derived mesh.

The exact BlueMap 5.23 feature-backport commit
`7e07f4e74ec1e92a6ead9aa1e66054af3e133aac` is an MIT-licensed compile and
test ABI only. Its BlueMapAPI submodule is pinned at
`285c9a60eff3ac2b0cab308ce1058d1565be0971`. The BlueMap license text is
retained as `LICENSE-BlueMap`; no BlueMap class is bundled. Flow Math 1.0.3 is
an indirect BlueMap ABI dependency and is not bundled.

JUnit and Mockito are test-only dependencies. The production POM and Gradle
module metadata declare no dependency.
