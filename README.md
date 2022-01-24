## Microstom
![GitHub](https://img.shields.io/github/license/KlainStom/microstom?style=flat-square)
![GitHub Repo stars](https://img.shields.io/github/stars/KlainStom/microstom?style=flat-square)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/KlainStom/microstom?style=flat-square)
![Lines of code](https://img.shields.io/tokei/lines/github/KlainStom/microstom?style=flat-square)
![GitHub all releases](https://img.shields.io/github/downloads/KlainStom/microstom/total?style=flat-square)

![GitHub last commit](https://img.shields.io/github/last-commit/KlainStom/microstom?style=flat-square)
![GitHub commit activity](https://img.shields.io/github/commit-activity/w/KlainStom/microstom?style=flat-square)
![GitHub commits since latest release (by SemVer)](https://img.shields.io/github/commits-since/KlainStom/microstom/latest?sort=semver&style=flat-square)
![GitHub release (latest by SemVer)](https://img.shields.io/github/downloads/KlainStom/microstom/latest/total?style=flat-square)

Microstom is a minecraft server based on [Minestom](https://github.com/Minestom/Minestom).

### Requirements
- java 17

### Features
- stop/restart command
- Mojang authentication
- proxy support

Nothing more. Not even a world to join.

Currently, it uses minecraft protocol version 1.18

### Restarting
Restarting the server calls the `./start.sh` script.
The generated script will start the server with no way to access the console.
So keep in mind that you will need an extension providing remote access or use tmux/screen in the `start.sh` to access the console.
