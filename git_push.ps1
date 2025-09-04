# Pull latest changes from remote
git pull

# Add all changes
git add .

# Prompt user for commit message
$commitMsg = Read-Host "Enter your commit message"

# Commit changes with the entered message
git commit -m "$commitMsg"

# Push changes to remote
git push
