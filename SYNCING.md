# Syncing OSS changes to fork

1. Make sure latest `main-dxp` branch is checked out
1. Create `feature/sync` branch `git checkout -b feature/sync`
1. Make sure OSS repository is added as separate remote. `git remote add oss git@github.com:TheWidlarzGroup/react-native-video.git`
1. Fetch all tags `git fetch --all --tags`
1. List OSS release tags `git ls-remote --tags oss`
1. Merge OSS release tag `git merge v6.X.X`
1. Solve conflicts
    1. Make sure CHANGELOG is ordered properly: OSS release X -> fork releases based on X -> OSS release Y
1. Check is sample is working
1. Add all changes `git add .`
1. Commit all changes `git commit -m "Sync 6.X.X"`
1. Push changes `git push -u origin feature/sync`