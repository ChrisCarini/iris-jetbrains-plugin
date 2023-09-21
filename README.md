# <img src="./src/main/resources/META-INF/pluginIcon.svg" width="32" /> Iris JetBrains Plugin

[![GitHub License](https://img.shields.io/github/license/ChrisCarini/iris-jetbrains-plugin?style=flat-square)](https://github.com/ChrisCarini/iris-jetbrains-plugin/blob/main/LICENSE)
[![JetBrains IntelliJ Plugins](https://img.shields.io/jetbrains/plugin/v/18137-iris?label=Latest%20Plugin%20Release&style=flat-square)](https://plugins.jetbrains.com/plugin/18137-iris)
[![JetBrains IntelliJ Plugins](https://img.shields.io/jetbrains/plugin/r/rating/18137-iris?style=flat-square)](https://plugins.jetbrains.com/plugin/18137-iris)
[![JetBrains IntelliJ Plugins](https://img.shields.io/jetbrains/plugin/d/18137-iris?style=flat-square)](https://plugins.jetbrains.com/plugin/18137-iris)
[![All Contributors](https://img.shields.io/github/all-contributors/ChrisCarini/iris-jetbrains-plugin?color=ee8449&style=flat-square)](#contributors)
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

## Contributors

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/ChrisCarini"><img src="https://avatars.githubusercontent.com/u/6374067?v=4?s=100" width="100px;" alt="Chris Carini"/><br /><sub><b>Chris Carini</b></sub></a><br /><a href="#bug-ChrisCarini" title="Bug reports">🐛</a> <a href="#code-ChrisCarini" title="Code">💻</a> <a href="#doc-ChrisCarini" title="Documentation">📖</a> <a href="#example-ChrisCarini" title="Examples">💡</a> <a href="#ideas-ChrisCarini" title="Ideas, Planning, & Feedback">🤔</a> <a href="#maintenance-ChrisCarini" title="Maintenance">🚧</a> <a href="#question-ChrisCarini" title="Answering Questions">💬</a> <a href="#review-ChrisCarini" title="Reviewed Pull Requests">👀</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->