#!/usr/bin/env pwsh
<#
.SYNOPSIS
    Cross-platform Docker + install architecture for RunZo
    Supports Windows, macos, linux.
    Install docker if missing, validates .env, and runs docker-compose.

.NOTES
    save this file as setup-arch.ps1
    Run with: pwsh ./setup-arch.ps1
#>
# -------------------------------
# Global Settings
# -------------------------------
$LogFile = Join-Path (Get-Location) "setup.log"
function Log {
    param ([string]$Message, [string]$Level="INFO")
    $timestamp = (Get-Date).toString("yyyy-MM-dd HH:mm:ss")
    $logEntry = "[$timestamp] [$Level] $Message"
    Write-Host $logEntry
    Add-Content -Path $LogFile -Value $logEntry
}

function Install-Docker{
    param ([string]$OS)
    if($OS -eq "Windows"){
        Log "Installing Docker desktop for windows..."
        $dockerInstallerUrl = "https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe"
        $installerPath = "$env:TEMP\DockerDesktopInstaller.exe"
        Invoke-WebRequest -Uri $dockerInstallerUrl -OutFile $installerPath
        Start-Process -FilePath $installerPath -ArgumentList "install", "--quiet" -Wait -NoNewWindow

    }
    elseif ($OS -eq "Linux") {
        Log "Installing Docker & Docker Compose on Linux..."
        sudo apt-get update -y
        sudo apt-get install -y docker.io docker-compose
        sudo systemctl enable docker
        sudo systemctl start docker
    }elseif ($OS -eq "macOS") {
        Log "Installing Docker Desktop for macOS..."
        brew install --cask docker
    }
    else {
        throw "Unsupported OS: $OS"
    }

}
function Wait-Docker{
    Log "Waiting for Docker to start..."
    $maxRetries = 30
    $retry = 0
    while($retry -lt $maxRetries){
        try{

            docker version | Out-Null
            Log "✅ Docker is running."
            return

        }catch {
            Log "Docker not ready yet... retrying in 10 seconds."
            Start-Sleep -Seconds 10
            $retry++
        }
    }

    throw "❌ Docker did not start in time."
}
function Validate-EnvFile {
    param (
        [string]$ProjectDir,
        [string[]]$RequiredKeys
    )

    $envFile = Join-Path $ProjectDir ".env"
    if (-not (Test-Path $envFile)) {
        throw "❌ Missing .env file at $envFile. Please create it before running."
    }

    Log "✅ .env file found at $envFile"

    $envContent = Get-Content $envFile
    foreach ($key in $RequiredKeys) {
        if (-not ($envContent -match "^$key=")) {
            throw "❌ Missing required environment variable: $key in .env file."
        }
    }
    Log "✅ All required keys are present in .env."
}


# -------------------------------
# Main
# -------------------------------
Log "🚀 Starting Docker setup script..."

# Detect OS
if ($IsWindows) {
    $osType = "Windows"
} elseif ($IsLinux) {
    $osType = "Linux"
} elseif ($IsMacOS) {
    $osType = "macOS"
} else {
    throw "Unsupported OS"
}
Log "Detected OS: $osType"

# Check if Docker is installed
if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
    Log "⚠️ Docker not found. Installing..."
    Install-Docker -OS $osType
} else {
    Log "✅ Docker is already installed."
}

# Ensure docker is running
Wait-Docker

# Project directory (default = current directory)
$projectDir = (Get-Location).Path
Log "Using project directory: $projectDir"

# Validate .env file with required keys
$requiredKeys = @("ZOOKEEPER_PORT", "KAFKA_PORT", "KAFKA_UI_PORT")
Validate-EnvFile -ProjectDir $projectDir -RequiredKeys $requiredKeys

# Run docker compose
Log "Running docker compose..."
docker-compose -f setup-compose.yml --env-file .env up -d
if ($LASTEXITCODE -eq 0) {
    Log "✅ Docker compose setup completed successfully."
} else {
    Log "❌ Docker compose failed. Check logs above."
    exit 1
}


