#!/usr/bin/env bash

BIN_NAME=$(basename "$0")
COMMAND_NAME=$1
SUB_COMMAND_NAME=$2

sub_help () {
  echo "Usage: $BIN_NAME <command>"
  echo
  echo "Commands:"
  echo "   start            Start 2 docker containers; Iris and a backing MySQL"
  echo "   stop             Stop the 2 docker containers; Iris & MySQL"
  echo "   remove           Remove the 2 docker containers."
  echo "   stats            Display a live stream of Iris & MySQL containers' resource usage statistics"
  echo "   help             This help message"
}

sub_start () {
  if ! [[ -x "$(command -v docker)" ]]; then
    echo '[ERROR]: docker is not installed. Please install docker before proceeding!' >&2
    exit 1
  fi

  echo "  "
  echo "We will now launch 2 docker containers:"
  echo "  - MySQL"
  echo "  - Iris"
  echo "  "
  echo "Note: We will now prompt you for a root password for the MySQL docker container."
  echo "      This will be used when connecting Iris to MySQL."
  echo "  "

  # Read Password
  MYSQL_PASSWORD=password
  echo -n "MySQL Password [$MYSQL_PASSWORD]: "
  read -s MYSQL_PASSWORD
  echo
  # Run Command
  MYSQL_PASSWORD=${MYSQL_PASSWORD:-password}
  echo "Using MySQL root password of: ${MYSQL_PASSWORD}"


  # Create a MySQL instance specifically for Iris, should one not exist
  DOCKER_CONTAINER_NAME_MYSQL_IRIS=mysql_iris
  CONTAINER_NAME=${DOCKER_CONTAINER_NAME_MYSQL_IRIS}
  if [[ ! "$(docker ps -q -f name=^/${CONTAINER_NAME}$)" ]]; then
    echo "${CONTAINER_NAME} container does exist..."
    if [[ "$(docker ps -q -f status=exited -f name=^/${CONTAINER_NAME}$)" ]]; then
        echo "${CONTAINER_NAME} container in exited state, removing..."
        docker rm ${CONTAINER_NAME}
        echo "${CONTAINER_NAME} container removed."
    fi
    echo "Starting ${CONTAINER_NAME} container..."
    docker run \
      --name=${CONTAINER_NAME} \
      -e MYSQL_ROOT_HOST=% \
      -e MYSQL_ROOT_PASSWORD=${MYSQL_PASSWORD} \
      -p 33060:33060 \
      -p 3306:3306 \
      -d mysql/mysql-server:5.7 \
      --sql-mode=""

    echo "${CONTAINER_NAME} container started."
  fi

  DOCKER_CONTAINER_NAME_IRIS=iris
  CONTAINER_NAME=${DOCKER_CONTAINER_NAME_IRIS}
  if [[ ! "$(docker ps -q -f name=^/${CONTAINER_NAME}$)" ]]; then
    echo "${CONTAINER_NAME} container does exist..."
    if [[ "$(docker ps -q -f status=exited -f name=^/${CONTAINER_NAME}$)" ]]; then
        echo "${CONTAINER_NAME} container in exited state, removing..."
        docker rm ${CONTAINER_NAME}
        echo "${CONTAINER_NAME} container removed."
    fi
    echo "Starting ${CONTAINER_NAME} container..."
    docker run \
      --name iris \
      --link mysql_iris:mysql \
      -e DOCKER_DB_BOOTSTRAP=1 \
      -e IRIS_CFG_DB_USER=root \
      -e IRIS_CFG_DB_PASSWORD=${MYSQL_PASSWORD} \
      -e IRIS_CFG_DB_HOST=mysql \
      -p 16649:16649 \
      -d quay.io/iris/iris:latest
    echo "${CONTAINER_NAME} container started."
  fi

  echo "You should now be able to access Iris at http://localhost:16649"
}

sub_stop () {
  echo "Stopping containers..."
  docker stop iris
  docker stop mysql_iris
  echo "Containers stopped."
}

sub_remove () {
  sub_stop
  echo "Removing containers..."
  docker rm iris
  docker rm mysql_iris
  echo "Containers removed."
}

sub_stats () {
  docker stats iris mysql_iris
}

case $COMMAND_NAME in
  "" | "-h" | "--help")
    sub_help
    ;;
  *)
    shift
    sub_${COMMAND_NAME} $@
    if [ $? = 127 ]; then
      echo "'$COMMAND_NAME' is not a known command or has errors." >&2
      sub_help
      exit 1
    fi
    ;;
esac