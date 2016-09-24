#!/bin/bash
cd $(dirname $(dirname $0))
git clone --branch=develop https://github.com/star-diopside/spark-commons.git
cd spark-commons
./gradlew install
