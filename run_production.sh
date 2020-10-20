#!/bin/sh
mvn compile
mvn exec:java -Dspring.profiles.active=production -Dexec.mainClass="projekti.App"
