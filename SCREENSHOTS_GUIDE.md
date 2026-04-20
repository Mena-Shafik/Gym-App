# How to Capture App Screenshots

This guide explains how to take screenshots of the Gym app for documentation purposes.

## Using Android Studio Emulator

### Method 1: Built-in Screenshot Tool (Easiest)
1. Run the app in Android Studio emulator
2. In the emulator window, click the **Screenshot** button (camera icon) in the right toolbar
3. Screenshots are automatically saved to your computer
4. Save them to the `screenshots/` folder with descriptive names

### Method 2: Android Device Monitor
1. Connect device or ensure emulator is running
2. In Android Studio: **Tools** → **Device Manager** 
3. Right-click the device and select **Screenshot**
4. Save to `screenshots/` folder

### Method 3: ADB Command
```bash
# Take a screenshot on device/emulator
adb shell screencap -p /sdcard/screenshot.png

# Pull it to your computer
adb pull /sdcard/screenshot.png

# Move it to the screenshots folder (example)
mv screenshot.png ./screenshots/01_login_screen_light.png
```

## Using Physical Device

### Method 1: Built-in Device Screenshot
- **Android 4.0+**: Press Power + Volume Down simultaneously
- Screenshots appear in Photos app → Screenshots folder
- Transfer via USB cable to `screenshots/` folder

### Method 2: Wireless via ADB
```bash
# Enable USB Debugging on device
# Connect device via USB
adb devices  # Verify device is connected

# Take screenshot
adb shell screencap -p /sdcard/DCIM/screenshot.png
adb pull /sdcard/DCIM/screenshot.png ./screenshots/
```

## Screenshot Naming Convention

Use this naming scheme for consistency:

```
# Authentication Flows
01_login_screen_light.png
02_login_screen_dark.png
03_signup_screen_light.png
04_signup_screen_dark.png
05_biometric_login_screen_light.png
06_biometric_login_screen_dark.png
07_profile_creation_screen_light.png
08_profile_creation_screen_dark.png

# Home & Dashboard
09_home_screen_light.png
10_home_screen_dark.png

# Exercise Library
11_exercise_tab_screen_light.png
12_exercise_tab_screen_dark.png
13_muscle_group_list_light.png
14_muscle_group_list_dark.png
15_exercise_detail_screen_light.png
16_exercise_detail_screen_dark.png

# Workout Management
17_workout_type_list_light.png
18_workout_type_list_dark.png
19_workout_detail_screen_light.png
20_workout_detail_screen_dark.png
21_workout_list_light.png
22_workout_list_dark.png
23_add_edit_workout_light.png
24_add_edit_workout_dark.png

# Equipment
25_equipment_list_light.png
26_equipment_list_dark.png
27_equipment_detail_screen_light.png
28_equipment_detail_screen_dark.png

# Profile & User
29_profile_screen_light.png
30_profile_screen_dark.png
31_profile_edit_screen_light.png
32_profile_edit_screen_dark.png

# Progress & Schedule
33_schedule_screen_light.png
34_schedule_screen_dark.png
35_progress_screen_light.png
36_progress_screen_dark.png

# Settings
37_settings_screen_light.png
38_settings_screen_dark.png
```

## Optimal Screenshot Settings

### Screen Size
- **Emulator**: Set to Pixel 5 or Pixel 6 (1080x2340px)
- **Device**: Any modern Android phone works
- **Aspect Ratio**: 9:20 recommended

### Best Practices
1. **Clear State**: Start fresh navigation to each screen
2. **Good Lighting**: Ensure text is clearly visible
3. **Dark Mode**: Take screenshots in both light and dark themes
4. **No Notifications**: Clear notification bar for cleaner screenshots
5. **Language**: Use English for consistency
6. **Content**: Use realistic sample data (user names, workout plans, etc.)

## Adding Screenshots to README

After saving screenshots to the `screenshots/` folder, add them to the README:

```markdown
### Authentication Flows

![Login Light](screenshots/01_login_screen_light.png)
![Login Dark](screenshots/02_login_screen_dark.png)
![Sign Up Light](screenshots/03_signup_screen_light.png)
![Sign Up Dark](screenshots/04_signup_screen_dark.png)
```

> You do not need every planned screenshot. A representative subset is enough.

## Editing Screenshots (Optional)

### Add Text/Arrows for Clarity
- **Tool**: Paint.NET, GIMP, or Photoshop
- **Size**: Keep text readable
- **Format**: PNG or JPG

### Create Mockups
- **Tool**: Adobe XD, Figma, or Sketch
- **Include**: Device frame around screenshot
- **Size**: 400-800px width for web display

## Publishing Screenshots

### GitHub Repository
1. Commit screenshots to `screenshots/` folder
2. Reference in README.md with relative paths
3. Push to repository

### Other Platforms
- **Google Play**: Screenshots must follow specific dimensions
- **App Stores**: Usually 1080x1920px or 1440x2560px
- **Documentation**: 800-1200px width recommended

## Troubleshooting

### Screenshots not appearing in file system
- Check emulator storage location
- Verify ADB permissions: `adb shell pm grant com.example.gym android.permission.READ_EXTERNAL_STORAGE`
- Restart emulator if needed

### Low resolution screenshots
- Ensure device/emulator is at native resolution
- Check if screenshot tool is scaling down
- Use ADB method for full resolution

### Can't find screenshots on device
- Check DCIM/Screenshots folder
- Try alternative locations: Pictures, Downloads
- Use file manager app on device

## Recommended Screenshot Flow

1. Build and run app: `./gradlew :app:installDebug`
2. Navigate to each screen following the user flow
3. Take screenshot at each major screen
4. Capture both light and dark themes
5. Save with consistent naming
6. Add to README with proper formatting
7. Commit to repository

## Quick Command to Take All Screenshots at Once

Create a batch script (`take_screenshots.sh`):

```bash
#!/bin/bash
# Take screenshots from emulator

SCREENSHOT_DIR="./screenshots"
mkdir -p $SCREENSHOT_DIR

# List of screens to capture (requires manual navigation between each)
echo "Navigate to Login Screen (Light) and press Enter..."
read
adb shell screencap -p /sdcard/01_login_light.png
adb pull /sdcard/01_login_light.png $SCREENSHOT_DIR/01_login_screen_light.png

echo "Navigate to Home Screen (Dark) and press Enter..."
read
adb shell screencap -p /sdcard/10_home_dark.png
adb pull /sdcard/10_home_dark.png $SCREENSHOT_DIR/10_home_screen_dark.png

# ... repeat for other screens

echo "All screenshots captured!"
```

Run with: `bash take_screenshots.sh`

---

For questions or issues, refer to the main README.md file.
