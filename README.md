# runzo-microservices

## Prerequisites

- PowerShell Core
 (pwsh) installed
- Internet connection for Docker installation
- .env file present in project root with required keys.
- setup-compose.yml file in project root

## Setup Instructions
1. Clone the repo and open a terminal

```
git clone <your-repo-url>
cd <your-repo>

```

## Run the script
```
pwsh ./setup-docker.ps1
```
## On Windows (PowerShell 5.1):
```
powershell -ExecutionPolicy Bypass -File .\setup-docker.ps1
```
## Logging
- Script logs will appear in real-time in your terminal.
- A full log is also saved in `setup.log`

# Verification
After running the script:
```
docker ps
```
## ⚠️ Troubleshooting
Docker didn’t start: Make sure virtualization is enabled (Hyper-V/WSL2 for Windows, Docker Engine for Linux).
Permission issues on Linux: Run script with sudo pwsh.
Missing variables: Check .env file has all required keys.







