@echo off
REM Gym App - Automated Test Runner (Windows Batch)
REM This script runs unit tests and provides a summary

echo.
echo =========================================
echo Gym App - Running Unit Tests
echo =========================================
echo.

REM Navigate to the project root
cd /d "%~dp0"

REM Run the tests
echo Running: gradlew.bat testDebugUnitTest
echo.

call gradlew.bat testDebugUnitTest

REM Capture the exit code
set TEST_EXIT_CODE=%ERRORLEVEL%

echo.
echo =========================================

REM Check if tests passed
if %TEST_EXIT_CODE% equ 0 (
    echo Test Results: PASSED ✓
    echo =========================================
    exit /b 0
) else (
    echo Test Results: FAILED ✗
    echo =========================================
    exit /b 1
)

