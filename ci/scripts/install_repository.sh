#!/bin/bash
rm -rf ~/.gradle
rm -rf ~/.m2
cd $(dirname $(dirname $0))
git clone -b develop https://github.com/star-diopside/silver-commons.git
cd silver-commons
./gradlew clean publishToMavenLocal
