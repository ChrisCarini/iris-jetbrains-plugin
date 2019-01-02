# Iris IntelliJ Plugin
A plugin to connect to an Iris installation and alert the user to new incidents.

## Getting started
_to-do..._

## TO-DO
Feel free to submit a PR for any of the below features - this list acts as my 'todo' for things I'd still like to 
see accomplished with this repository.

- Add an option to read out-loud Iris notifications; below is how to do this, wire in accordingly.
```java
NotificationsConfiguration.getNotificationsConfiguration()
        .changeSettings(IRIS_NOTIFICATION_GROUP.getDisplayId(), IRIS_NOTIFICATION_GROUP.getDisplayType(), true, false);
```
- Shell scripts for getting a local deploy of Iris running for easier testing
- The ["Getting started"](#getting-started) section above