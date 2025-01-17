#!/bin/bash

###  Java file scorer, using checkstyle
###  Gauthier HEISS, 24/11/2023
###  gauthier.heiss@esiea.fr
###
###  Usage : ./score.sh [file]
###  If there is no file argument, the scorer will find recurcively all .java files

# Stop on error
set -e

# Clear "output.txt" file
echo "" > output.txt

# Check if sun_hecks.xml exists
if [[ ! -f /tmp/java-file-scorer/sun_checks.xml ]]; then
    mkdir /tmp/java-file-scorer/
    cp sun_checks.xml /tmp/java-file-scorer/sun_checks.xml
fi

# If there is no launching argument, find all files
if [[ $# -eq 0 ]]; then
    # Run checkstyle on all .java recursively, and append results to output.txt
    java -jar /tmp/java-file-scorer/checkstyle-10.12.5-all.jar -c /tmp/java-file-scorer/sun_checks.xml . >> output.txt || true
    # Count all line of codes in all .java recurcively, ignoring comments and empty lines
    count=$(find . -name "*.java" -type f -exec grep -vE '^\s*(//|\*)' {} \; | wc -l)
else
    if [[ -f $1 ]]; then
        # Run checkstyle only on asked file
        java -jar /tmp/java-file-scorer/checkstyle-10.12.5-all.jar -c /tmp/java-file-scorer/sun_checks.xml "$1" >> output.txt || true
        # Count lines in the asked file, ignoring comments and empty lines
        count=$(grep -c -vE '^\s*(//|\*)' "$1")
    else
        echo -e "\e[31mNo such file or directory \"$1\"\e[0m"
        echo "---- Final score: ----"
        echo "0.00"
        exit
    fi
fi

# Removing ignored tests
sed -i '/NewlineAtEndOfFile/d' output.txt
sed -i '/RegexpSingleline/d' output.txt
sed -i '/JavadocPackage/d' output.txt
sed -i '/FileTabCharacter/d' output.txt
sed -i '/JavadocVariable/d' output.txt
sed -i '/MissingJavadocMethod/d' output.txt
sed -i '/HiddenField/d' output.txt

# Color lines in output.txt
sed -i -r 's/ERROR/\x1b[31m&\x1b[0m/g' output.txt
sed -i -r 's/WARN/\x1b[33m&\x1b[0m/g' output.txt
sed -i -r 's/INFO/\x1b[32m&\x1b[0m/g' output.txt

# Displaying output.txt
cat output.txt

# Line break
echo -e "\n"

# If there is no code, give 0 as score
if [[ ${count} == 0 ]]; then
    echo -e "\e[31mNo code to analyze. Do you have .java files in this directory or its subdirectories?\e[0m"
    echo "---- Final score: ----"
    echo "0.00"
    exit
fi

# If there is a parsing error (which is fatal), give 0 as score
if grep -q "Parsing stopped here" output.txt; then
    echo -e "\e[31mFatal error in parsing\e[0m"
    echo "---- Final score: ----"
    echo "0.00"
    exit
fi

# Count stlye, info, warning and errors. "|| true" is used to avoid error if there is no result
errors=$(grep -c "ERROR" < output.txt || true)
warnings=$(grep -c "WARN" < output.txt || true)
infos=$(grep -c "INFO" < output.txt || true)

# Displaying errors count
echo "---- Results: ----"
echo "Lines: ${count}"
echo "Errors: ${errors}"
echo "Warnings: ${warnings}"
echo "Info: ${infos}"

# Compute score
score=$(echo "scale=5; 10 - ((${errors} + ${warnings} + ${infos}) / ${count} * 10)" | bc | awk '{printf "%.2f\n", $0}')

# If score is negative, set to 0
if [[ ${score} == *-* ]]; then
    score="0.00"
fi

# Show score
echo -e "\n---- Final score: ----"
echo "${score}"
