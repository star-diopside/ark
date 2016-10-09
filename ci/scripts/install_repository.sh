#!/bin/bash
rm -rf ~/.m2
cd $(dirname $(dirname $0))
git clone --branch=develop https://github.com/star-diopside/silver-commons.git
cd silver-commons
./gradlew clean install
