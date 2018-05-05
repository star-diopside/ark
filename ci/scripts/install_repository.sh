#!/bin/bash
rm -rf ~/.gradle
rm -rf ~/.m2
cd $(dirname $(dirname $0))
git clone -b v1.0.1 https://github.com/star-diopside/silver-commons.git
cd silver-commons
./gradlew clean install
