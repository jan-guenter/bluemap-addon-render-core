# Pilot contract

The first migration cohort is LaserIO, Pipez, and Chipped. LaserIO proves
coexistence with the shared runtime source module, Pipez provides a second
small transformed/emissive call path, and Chipped proves coexistence with the
Athena resource-model source module plus its existing `Sample` behavior test.
CobbleFurnies remains the initial untouched control.

## Frozen baselines

| Consumer | Current main before migration | Frozen matching source |
| --- | --- | --- |
| LaserIO | `e18f93588eacd3a188190a0318b32f11a038f798` | `2148a344b1ae78e77b95aa2baa51efe46c1357e8` |
| Pipez | `e1ffb394cec4e9860433f7c187b3d09afb6e30b3` | `fa3e773a7d1b7e9af52277bf104e70f704b0bb2a` |
| Chipped | `cc5ab1b2af6e447db775f12e659d3dea980350cb` | `c474a82b6bfd1b4173d119cb1e053a5458167e4b` |
| CobbleFurnies control | `5f8aaf610b9cbc767ddadc94a22402f033999e5b` | `eea5407dbbd162cbe4dd8fc5bc247f6617cf5d98` |

All profiles target ATMons 1.2.0, Minecraft 1.21.1, NeoForge 21.1.248,
Java 21, and the exact BlueMap 5.23 feature target recorded in `README.md`.

## Consumer acceptance

Each consumer needs a reviewed pull request and a new version because package
relocation changes its JAR and sources JAR. The migration may remove only the
local `FaceLighting`, add the pinned module source directory, and change
necessary imports and archive/provenance checks. Emitters, profiles,
renderers, fixtures, and failure policies stay unchanged.

Require every isolated child gate and prove unchanged non-relocated archive
entries. Then run the complete ATMons 1.2.0 combined suite with all 51 add-ons
and a full restart. Require all activation markers, gallery assertions, and
no duplicate-class or linkage failure.
