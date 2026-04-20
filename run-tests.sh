#!/bin/bash

# Gym App - Automated Test Runner
# This script runs unit tests and provides a summary

echo "========================================="
echo "Gym App - Running Unit Tests"
echo "========================================="
echo ""

# Navigate to the project root
cd "$(dirname "$0")" || exit 1

# Run the tests
echo "Running: ./gradlew testDebugUnitTest"
echo ""

./gradlew testDebugUnitTest

# Capture the exit code
TEST_EXIT_CODE=$?

echo ""
echo "========================================="

# Check if tests passed
if [ $TEST_EXIT_CODE -eq 0 ]; then
    echo "✓ Tests PASSED"
    echo "========================================="
    exit 0
else
    echo "✗ Tests FAILED"
    echo "========================================="
    exit 1
fi

