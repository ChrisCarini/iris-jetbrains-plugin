"""
Quick Use:
    python createIncidents.py --host http://localhost:16649 --plan demo-test-foo

By default, the above command will create an incident for the `foo` user.

Set the `username` to `foo` in the IDE to receive these notifications.
"""
import argparse
from irisclient import IrisClient
from optparse import Values
from typing import List, Tuple


def main(arguments: Tuple[Values, List[str]]) -> None:
    print(f"Base Hostname: {arguments.host}")
    print(f"Application:   {arguments.app}")
    print(f"Key:           *****{arguments.key[-5:]}")
    print(f"Plan Name:     {arguments.plan}")
    print()

    client = IrisClient(app=arguments.app, key=arguments.key, api_host=arguments.host)

    for x in range(arguments.count):
        print(f"Creating incident {x + 1} of {arguments.count}...")
        # create an incident
        resp = client.incident(arguments.plan, context={'count': x + 1, 'key-foo': 'abc', 'key-bar': 1})
        print(f'Incident ID: {resp}')
        # # send an adhoc notification
        # print(client.notification(role='user', target='alice', priority='urgent', subject='Yo'))


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('--host', help='The base hostname to request against', required=True)
    parser.add_argument('--key', help='The key to use for authenticating the Iris Client',
                        default="a7a9d7657ac8837cd7dfed0b93f4b8b864007724d7fa21422c24f4ff0adb2e49", required=False)
    parser.add_argument('--app', help='The application to use for the client', default="Autoalerts", required=False)
    parser.add_argument('--plan', help='The plan to create an incident against', required=True)
    parser.add_argument('-c', '--count', help='The number of incidents to create', type=int, default=1)
    arguments = parser.parse_args()

    main(arguments)
