<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# IntelliJ Platform Plugin Template Changelog

## [Unreleased]
### Added

### Changed
- Upgrading IntelliJ to 2021.3

### Deprecated

### Removed

### Fixed

### Security

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