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
        
    }

}

