@echo off
echo 🚀 Starting Process...
echo ---------------------------------------------------

echo 1. Initializing Git...
git init

echo 2. Adding files...
git add .

echo 3. Committing changes...
git commit -m "Upload Android App"

echo 4. Setting branch to main...
git branch -M main

echo 5. Removing old remote (if exists)...
git remote remove origin 2>nul

echo 6. Adding remote repository...
git remote add origin https://github.com/hussamgalal999/pixshop.git

echo 7. Pushing to GitHub...
echo (If asked for password, use your GitHub Token)
echo ---------------------------------------------------
git push -u origin main

echo ---------------------------------------------------
echo ✅ Done! Go check GitHub Actions now.
pause
