<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# IntelliJ Platform Plugin Template Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Security

## [1.2.5] - 2024-03-13

### Changed
- Upgrading IntelliJ from 2023.3.4 to 2023.3.5

## [1.2.4] - 2024-02-14

### Changed
- Upgrading IntelliJ from 2023.3.3 to 2023.3.4

## [1.2.3] - 2024-01-30

### Changed
- Upgrading IntelliJ from 2023.3.2 to 2023.3.3

## [1.2.2] - 2023-12-21

### Changed
- Upgrading IntelliJ from 2023.3.1 to 2023.3.2

## [1.2.1] - 2023-12-14

### Changed
- Upgrading IntelliJ from 2023.3 to 2023.3.1

## [1.2.0] - 2023-12-09

### Changed
- Upgrading IntelliJ from 2023.2.5 to 2023.3.0

## [1.1.5] - 2023-11-10

### Changed
- Upgrading IntelliJ from 2023.2.4 to 2023.2.5

## [1.1.4] - 2023-10-28

### Changed
- Upgrading IntelliJ from 2023.2.3 to 2023.2.4

## [1.1.3] - 2023-10-12

### Changed
- Upgrading IntelliJ from 2023.2.2 to 2023.2.3

## [1.1.2] - 2023-09-21

### Changed
- Upgrading IntelliJ from 2023.2.1 to 2023.2.2

## [1.1.1] - 2023-09-06

### Changed
- Upgrading IntelliJ from 2023.2 to 2023.2.1

## [1.1.0] - 2023-07-27

### Changed
- Upgrading IntelliJ from 2023.1.5 to 2023.2.0

## [1.0.5] - 2023-07-26

### Changed
- Upgrading IntelliJ from 2023.1.4 to 2023.1.5

## [1.0.4] - 2023-07-14

### Changed
- Upgrading IntelliJ from 2023.1.3 to 2023.1.4

## [1.0.3] - 2023-06-22

### Changed
- Upgrading IntelliJ from 2023.1.2 to 2023.1.3

## [1.0.2] - 2023-05-17

### Changed
- Upgrading IntelliJ from 2023.1.1 to 2023.1.2

## [1.0.1] - 2023-04-29

### Changed
- Upgrading IntelliJ from 2023.1 to 2023.1.1

## [1.0.0] - 2023-03-29

### Changed
- Upgrading IntelliJ from 2022.3.3 to 2023.1.0

## [0.3.3] - 2023-03-13

### Changed
- Upgrading IntelliJ from 2022.3.2 to 2022.3.3

## [0.3.2] - 2023-02-04

### Changed
- Upgrading IntelliJ from 2022.3.1 to 2022.3.2

## [0.3.1] - 2022-12-28

### Changed
- Upgrading IntelliJ from 2022.3 to 2022.3.1

## [0.3.0] - 2022-12-28

## [0.2.3] - 2022-11-28

### Changed
- Upgrading IntelliJ from 2022.2 to 2022.2.4

## [0.2.2] - 2022-07-29

### Changed
- Upgrading IntelliJ to 2022.2

## [0.2.1] - 2022-06-03

### Changed
- Upgrading IntelliJ to 2022.1.2

### Fixed
- Fix stacktrace when testing Iris host connection (changed in [linkedin/iris#515] on 2019-05-01) (#24)
- Strip trailing slash in hostname (#47)

## [0.2.0] - 2022-04-14

### Changed
- Upgrading IntelliJ to 2022.1

## [0.1.0] - 2021-12-04

### Changed
- Upgrading IntelliJ to 2021.3

## [0.0.5] - 2021-10-16

### Added
- Restructured file to extract all variables into file.
- Adding ability to publish to different channels based on SemVer pre-release labels.
- Adding [JetBrains Plugin Signing](https://plugins.jetbrains.com/docs/intellij/plugin-signing.html)
- Update Dependabot to include Gradle dependencies.
- Adding GitHub build & release workflows.
- Adding JetBrains Qodana (experimental, testing only)
- Adding Gradle wrapper files

### Changed
- Upgrading Gradle to 6.6
- Upgrading IntelliJ to 2021.2.2
- Upgrading IntelliJ Gradle plugin to 1.2.0
- Upgrading `requests` Python package for `createIncidents.py` script

### Fixed
- Fixed `deploy-iris.sh` script to startup `iris` with a `GOOD` `/healthcheck` endpoint.

## [0.0.4] - 2019-01-24

### Added
- Prepare for 2019.1 release (based off of build 191.5849.21).

## [0.0.3] - 2019-01-15

### Added
- Exposing option to limit Incidents API number of results and changing the default to 100 results. See <a href="https://github.com/ChrisCarini/iris-jetbrains-plugin/issues/2">GitHub Issue #1</a> for more info.

## [0.0.2] - 2019-01-14

### Fixed
- Creation of "Advanced Options" section. See <a href="https://github.com/ChrisCarini/iris-jetbrains-plugin/issues/2">GitHub Issue #2</a> for more info.

## [0.0.1] - 2019-01-01

### Added
- Initial release.

[Unreleased]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.2.5...HEAD
[1.2.5]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.2.4...v1.2.5
[1.2.4]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.2.3...v1.2.4
[1.2.3]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.2.2...v1.2.3
[1.2.2]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.2.1...v1.2.2
[1.2.1]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.2.0...v1.2.1
[1.2.0]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.1.5...v1.2.0
[1.1.5]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.1.4...v1.1.5
[1.1.4]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.1.3...v1.1.4
[1.1.3]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.1.2...v1.1.3
[1.1.2]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.1.1...v1.1.2
[1.1.1]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.1.0...v1.1.1
[1.1.0]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.0.5...v1.1.0
[1.0.5]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.0.4...v1.0.5
[1.0.4]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.0.3...v1.0.4
[1.0.3]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.0.2...v1.0.3
[1.0.2]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.0.1...v1.0.2
[1.0.1]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v1.0.0...v1.0.1
[1.0.0]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.3.3...v1.0.0
[0.3.3]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.3.2...v0.3.3
[0.3.2]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.3.1...v0.3.2
[0.3.1]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.3.0...v0.3.1
[0.3.0]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.2.3...v0.3.0
[0.2.3]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.2.2...v0.2.3
[0.2.2]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.2.1...v0.2.2
[0.2.1]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.2.0...v0.2.1
[0.2.0]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.0.5...v0.1.0
[0.0.5]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.0.4...v0.0.5
[0.0.4]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.0.3...v0.0.4
[0.0.3]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.0.2...v0.0.3
[0.0.2]: https://github.com/ChrisCarini/iris-jetbrains-plugin/compare/v0.0.1...v0.0.2
[0.0.1]: https://github.com/ChrisCarini/iris-jetbrains-plugin/commits/v0.0.1
