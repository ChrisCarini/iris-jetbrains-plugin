import argparse
from irisclient import IrisClient
from pprint import pprint


def main(host: str, app: str, key: str, plan: str):
    client = IrisClient(app=app, key=key, api_host=host)
    print("Creating incident...")
    # create an incident
    pprint(client.incident(plan, context={'key-foo': 'abc', 'key-bar': 1}), indent=2)
    # # send an adhoc notification
    # print(client.notification(role='user', target='alice', priority='urgent', subject='Yo'))


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('--host', help='The base hostname to request against', required=True)
    parser.add_argument('--app', help='The application to use for the client', default="Autoalerts", required=False)
    parser.add_argument('--key', help='The key to use for authenticating the Iris Client', required=True)
    parser.add_argument('--plan', help='The plan to create an incident against', required=True)
    arguments = parser.parse_args()

    # show values #
    print("Base hostname: %s" % arguments.host)
    print("Application: %s" % arguments.app)
    key = arguments.key
    print("Key: %s%s".format("*" * (len(key) - 5), key[-5:]))
    print("Plan Name: %s" % arguments.plan)

    main(arguments.host, arguments.app, arguments.key, arguments.plan)
