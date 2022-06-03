#!/usr/bin/env bash

# Borrowed bash subcommand template from: https://gist.github.com/waylan/4080362
prog_name=$(basename $0)

sub_help() {
  echo "Usage: $prog_name <subcommand> [options]\n"
  echo "Subcommands:"
  echo "    init      Start the Iris Stack"
  echo ""
  echo "    start     Start the Iris Stack"
  echo "    stop      Stop the Iris Stack"
  echo "    status    Status of Iris Stack"
  echo "    restart   Restart the Iris Stack"
  echo ""
  echo "    purge     Stop & delete all containers associated with Iris"
  echo ""
  echo "Note: This script assumes you have 'docker-compose' installed."
  echo ""
}

access_info() {
  echo "#################################################"
  echo "##                                             ##"
  echo "##   Access Iris via: http://localhost:16649   ##"
  echo "##                                             ##"
  echo "##    Username: demo                           ##"
  echo "##    Password: demo                           ##"
  echo "##                                             ##"
  echo "#################################################"
}

sub_init() {
  if ! [[ -x "$(command -v docker)" ]]; then
    echo '[ERROR]: docker is not installed. Please install docker before proceeding!' >&2
    exit 1
  fi

  if [[ ! -d ./iris ]]; then
    # Clone the Iris GitHub repo, if it does not exist
    git clone https://github.com/linkedin/iris.git
  fi

  pushd ./iris >/dev/null || exit

  # Pull the latest version
  git pull --hard

  # Bring up the containers
  docker-compose up -d --force-recreate --renew-anon-volumes

  # Test for Iris being ready
  function test_iris_status() {
    curl http://localhost:16649/healthcheck 2>&1
  }
  until test_iris_status | grep -q -m 1 "GOOD"; do
    printf "[$(date +"%Y-%m-%dT%H:%M:%S%z")] Waiting for Iris to be available...\r"
    sleep 1
  done
  echo 'Kibana ready!'

  popd >/dev/null || exit

  sub_status
}

sub_start() {
  pushd ./iris >/dev/null || exit
  docker-compose up -d --force-recreate --renew-anon-volumes
  popd >/dev/null || exit

  sub_status
}

sub_stop() {
  pushd ./iris >/dev/null || exit
  docker-compose stop
  popd >/dev/null || exit
}

sub_status() {
  pushd ./iris >/dev/null || exit
  docker-compose ps
  popd >/dev/null || exit

  access_info
}

sub_restart() {
  sub_stop
  sub_start
}

sub_purge() {
  pushd ./iris >/dev/null || exit
  docker-compose rm -s -v -f
  docker-compose down -v
  docker volume prune -f
  popd >/dev/null || exit
}

subcommand=$1
case $subcommand in
"" | "-h" | "--help")
  sub_help
  ;;
*)
  shift
  sub_${subcommand} $@
  if [[ $? = 127 ]]; then
    echo "Error: '$subcommand' is not a known subcommand." >&2
    echo "       Run '$prog_name --help' for a list of known subcommands." >&2
    exit 1
  fi
  ;;
esac
