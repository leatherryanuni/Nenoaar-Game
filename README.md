# UniSim

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and a main class extending `Game` that sets the first screen.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

## Download Guide
If you intend to download the jar file of the game:
 - Windows: No issues.
 - MacOS: Will most likely say that the file is from an unidentified developer, to resolve this, go to System Settings -> Privacy & Security. Under the security heading, there should be a notification regarding this, and below it, click 'Open Anyway'.

## Attribution 
 - LibGDX is licensed under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
 - 2D Art Tileset by [rubberduck](https://opengameart.org/content/colony-sim-extended-version) and [Buch] is licensed under [CC0 1.0 Universal (Public Domain Dedication)](http://creativecommons.org/publicdomain/zero/1.0/).
 - UI skin "Shade UI" by [Raymond "Raeleus" Buckley](https://ray3k.wordpress.com/artwork/shade-ui-skin-for-libgdx/) is licensed under [Creative Commons Attribution 4.0 International License](https://creativecommons.org/licenses/by/4.0/).
