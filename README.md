# Iris JetBrains Plugin

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