# Template

This projects provides an extended base of the Vaadin 21 starter project.

- Uses Gradle not maven
- Adds customizable authorization
- Better docker support

## Running the application

The project is a standard Gradle project. To run it from the command line,
`./gradlew run` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Gradle project. 

## Deploying to Production

To create a production build, call `./gradlew assembleDist`
This will build a Tar and Zip file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `build/distributions` folder after the build completes.

This tar/zip contains your complete packaged application.
The archives can be used for distribution with package managers or for installer creators.

Within the archives there is a `bin` folder which contains a windows and linux start script.
The command to start your app is as follows

(Linux)
```shell
./<applicationlocation>/bin/<appname>
```

(Windows)
```shell
C:\<applicationlocation>\bin\<appname>.bat
```

## Project structure

- `MainView.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/components/vaadin-app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.

## Useful links

- Read the documentation at [vaadin.com/docs](https://vaadin.com/docs).
- Follow the tutorials at [vaadin.com/tutorials](https://vaadin.com/tutorials).
- Watch training videos and get certified at [vaadin.com/learn/training](https://vaadin.com/learn/training).
- Create new projects at [start.vaadin.com](https://start.vaadin.com/).
- Search UI components and their usage examples at [vaadin.com/components](https://vaadin.com/components).
- Discover Vaadin's set of CSS utility classes that enable building any UI without custom CSS in the [docs](https://vaadin.com/docs/latest/ds/foundation/utility-classes). 
- Find a collection of solutions to common use cases in [Vaadin Cookbook](https://cookbook.vaadin.com/).
- Find Add-ons at [vaadin.com/directory](https://vaadin.com/directory).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/vaadin) or join our [Discord channel](https://discord.gg/MYFq5RTbBn).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin/platform).


## Deploying using Docker

To build the Dockerized version of the project, run

```
docker build . -t nitoncloud:latest
```

Once the Docker image is correctly built, you can test it locally using

```
docker run -p 8080:8080 nitoncloud:latest
```
