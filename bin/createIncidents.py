import argparse
from pprint import pprint

from irisclient import IrisClient


def main(host: str, app: str, key: str, plan: str, count: int):
    client = IrisClient(app=app, key=key, api_host=host)
    for x in range(count):
        print("Creating incident {} of {}...".format(x + 1, count))
        # create an incident
        pprint(client.incident(plan, context={'count': x + 1, 'key-foo': 'abc', 'key-bar': 1}), indent=2)
        # # send an adhoc notification
        # print(client.notification(role='user', target='alice', priority='urgent', subject='Yo'))


if __name__ == "__main__":
    """
    Quick Use:
        python createIncidents.py --host http://localhost:16649 --plan demo-test-foo
    """

    parser = argparse.ArgumentParser()
    parser.add_argument('--host', help='The base hostname to request against', required=True)
    parser.add_argument('--key', help='The key to use for authenticating the Iris Client',
                        default="a7a9d7657ac8837cd7dfed0b93f4b8b864007724d7fa21422c24f4ff0adb2e49", required=False)
    parser.add_argument('--app', help='The application to use for the client', default="Autoalerts", required=False)
    parser.add_argument('--plan', help='The plan to create an incident against', required=True)
    parser.add_argument('-c', '--count', help='The number of incidents to create', type=int, default=1)
    arguments = parser.parse_args()

    # show values #
    print("Base hostname: %s" % arguments.host)
    print("Application: %s" % arguments.app)
    key = arguments.key
    print("Key: {}{}".format("*" * (len(key) - 5), key[-5:]))
    print("Plan Name: %s" % arguments.plan)

    main(arguments.host, arguments.app, arguments.key, arguments.plan, arguments.count)
