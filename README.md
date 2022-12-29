# <img src="./src/main/resources/META-INF/pluginIcon.svg" width="32" /> Iris JetBrains Plugin

[![GitHub License](https://img.shields.io/github/license/ChrisCarini/iris-jetbrains-plugin?style=flat-square)](https://github.com/ChrisCarini/iris-jetbrains-plugin/blob/main/LICENSE)
[![JetBrains IntelliJ Plugins](https://img.shields.io/jetbrains/plugin/v/18137-iris?label=Latest%20Plugin%20Release&style=flat-square)](https://plugins.jetbrains.com/plugin/18137-iris)
[![JetBrains IntelliJ Plugins](https://img.shields.io/jetbrains/plugin/r/rating/18137-iris?style=flat-square)](https://plugins.jetbrains.com/plugin/18137-iris)
[![JetBrains IntelliJ Plugins](https://img.shields.io/jetbrains/plugin/d/18137-iris?style=flat-square)](https://plugins.jetbrains.com/plugin/18137-iris)
[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/ChrisCarini/iris-jetbrains-plugin/build.yml?branch=main&logo=GitHub&style=flat-square)](https://github.com/ChrisCarini/iris-jetbrains-plugin/actions/workflows/build.yml)
[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/ChrisCarini/iris-jetbrains-plugin/compatibility.yml?branch=main&label=IntelliJ%20Plugin%20Compatibility&logo=GitHub&style=flat-square)](https://github.com/ChrisCarini/iris-jetbrains-plugin/actions/workflows/compatibility.yml)

<!-- Plugin description -->
A plugin for JetBrains IDEs providing notifications from [Iris](https://github.com/linkedin/iris) within the JetBrains ecosystem of IDEs.
<!-- Plugin description end -->

## Development Notes

### Local Testing Notes

- Make use of the scripts in the `/bin` directory.
  - Quick Start:
    ```shell
    ./mkvenv.sh
    ./iris.sh start
    sleep 15
    python createIncidents.py --host http://localhost:16649 --plan demo-test-foo
    ```
- When configuring the sandbox IDE, set the below settings:
  - **Enabled:** checked
  - **Username:** `foo`
  - **Iris API Hostname:** http://localhost:16649
  - **Advanced Options:** _(as desired)_

## Contributing

Feel free to submit a PR for any of this [repositories issues](https://github.com/ChrisCarini/iris-jetbrains-plugin/issues) - or anything else, just make sure you include
adequate details in the request.