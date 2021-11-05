## Microstom
Microstom is a minecraft server based on [Minestom](https://github.com/Minestom/Minestom).

### Requirements
- java 17

### Features
- stop/restart command
- Mojang authentication
- proxy support

Nothing more. Not even a world to join.

Currently, it uses minecraft protocol version 1.17.1

### Restarting
Restarting the server calls the `./start.sh` script.
The generated script will start the server with no way to access the console.
So keep in mind that you will need an extension providing remote access or use tmux/screen in the `start.sh` to access the console.
