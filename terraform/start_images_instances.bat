@echo off
setlocal enabledelayedexpansion

echo Running terraform apply images...

cd ./create_images

call terraform apply || (
    echo An error occurred during terraform apply images.
    pause
    exit /b 1
)

if !errorlevel! equ 0 (
    echo Terraform apply images completed successfully!
    echo The resources have been created.
        
    pause
    
) else (
    echo Terraform apply images failed.
    pause
    exit /b 1
)

echo Running terraform apply instances...

cd ../create_instances

call terraform apply || (
    echo An error occurred during terraform apply instances.
    pause
    exit /b 1
)

if !errorlevel! equ 0 (
    echo Terraform apply instances completed successfully!
    echo The resources have been created.
        
    pause
    exit /b 0
) else (
    echo Terraform apply instances failed.
    pause
    exit /b 1
)