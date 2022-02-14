## Microstom
[![GitHub](https://img.shields.io/github/license/KlainStom/microstom?style=flat-square)](https://github.com/KlainStom/microstom/blob/master/LICENSE)
[![GitHub Repo stars](https://img.shields.io/github/stars/KlainStom/microstom?style=flat-square)](https://github.com/KlainStom/microstom/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/KlainStom/microstom?style=flat-square)](https://github.com/KlainStom/microstom/network/members)
[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/KlainStom/microstom?style=flat-square)](https://github.com/KlainStom/microstom/releases/latest)
[![GitHub all releases](https://img.shields.io/github/downloads/KlainStom/microstom/total?style=flat-square)](https://github.com/KlainStom/microstom/releases)

[![GitHub last commit](https://img.shields.io/github/last-commit/KlainStom/microstom?style=flat-square)](https://github.com/KlainStom/microstom/commits/master)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/w/KlainStom/microstom?style=flat-square)](https://github.com/KlainStom/microstom/pulse)
[![GitHub commits since latest release (by SemVer)](https://img.shields.io/github/commits-since/KlainStom/microstom/latest?sort=semver&style=flat-square)](https://github.com/KlainStom/microstom/commits/master)
[![GitHub release (latest by SemVer)](https://img.shields.io/github/downloads/KlainStom/microstom/latest/total?style=flat-square)](https://github.com/KlainStom/microstom/releases/latest)

Microstom is a minecraft server with [Minestom](https://github.com/Minestom/Minestom) as its core.

Requires Java 17.<br>
Supports 1.18 and 1.18.1 clients.

### Features
- `/stop`,`/end` and `/restart` command
- Mojang authentication
- proxy support (BungeeCord and Velocity)
- no default worlds, generators, ...
- a colored terminal (at least the log levels)

### Restarting
Restarting the server calls the `./start.sh` script.
The generated script will start the server with no way to access the console.
So keep in mind that you will need an extension providing remote access or use tmux/screen in the `start.sh` to access the console.
