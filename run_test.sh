#!/bin/sh
mvn compile
mvn exec:java -Dspring.profiles.active=test -Dexec.mainClass="projekti.App"
