#!/bin/bash

# Set Java Home
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

# Navigate to project directory
cd "$(dirname "$0")"

# Run the application
./mvnw spring-boot:run

