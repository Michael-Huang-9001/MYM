#!/bin/sh

echo "[Starting test!]"

# Prepare for compilation
echo "[Preparing for compilation]"

cd ../

pwd

# Compile java source files
echo "[Compiling source files]"

javac -cp "./mysql-connector-java-5.1.44-bin.jar:" ./src/DataSourceTester.java ./src/AgencyDB.java ./src/HousingLookup.java ./src/AgencyPrompt.java ./src/House.java ./src/User.java -d ./


# Run with txt file
echo "[Running]"
java -cp ./mysql-connector-java-5.1.44-bin.jar:. HousingLookup < ./test/input.txt >! ./test/output.txt

# Compare and output the diff
cd ./test
echo "[Test Result]"
diff -c ./golden.txt ./output.txt

