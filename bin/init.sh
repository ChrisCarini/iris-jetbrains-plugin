#!/usr/bin/env bash

# Create a virtual environment and install the requirements for the scripts
virtualenv -p /usr/local/bin/python3.7 venv
source venv/bin/activate
pip install -r requirements.txt