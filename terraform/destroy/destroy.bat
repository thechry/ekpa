@echo off
setlocal enabledelayedexpansion

echo Running instances terraform destroy...

cd ../create_instances

call terraform destroy || (
    echo An error occurred during instances terraform destroy.
    pause
    exit /b 1
)

if !errorlevel! equ 0 (
    echo Terraform destroy completed successfully!
    echo The resources have been destroyed.
        
    pause
    exit /b 0
) else (
    echo Terraform destroy failed.
    pause
    exit /b 1
)

echo Running images terraform destroy...

cd ../create_images

call terraform destroy || (
    echo An error occurred during images terraform destroy.
    pause
    exit /b 1
)

if !errorlevel! equ 0 (
    echo Terraform destroy completed successfully!
    echo The resources have been destroyed.
        
    pause
    exit /b 0
) else (
    echo Terraform destroy failed.
    pause
    exit /b 1
)