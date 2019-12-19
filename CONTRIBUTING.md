# How to contribute

Fork, then clone the repo:
```shell script
git clone <repourl>//TODO
```

Make sure the project is working
```shell script
./gradlew clean build test check
```

Make your changes and be sure that everything works fine.
```shell script
./gradlew clean build test check
```

Push to your fork and submit a pull request.

### How to test your changes
First way to test the changes you made is writing unit, functional and/or integration tests. We encourage you to write 
tests for the code you wrote. To run the tests on the project run:
```shell script
./gradlew test
```
For manual testing you can use any android project and add the plugin to your project as described on the [README.md](README.md).
After adding the plugin to your project you need to point its location to your local directory. To do this add:
```groovy
includeBuild('path/to/Android-Checklist-Plugin/checklist'){
    dependencySubstitution {
        substitute module('com.commencis.checklist:checklist') with project(':')
    }
}
```
to your settings.gradle file. After that you can make your changes and run the checklist task on your android project.

### Adding a new task
If you want to add a new task, there are some steps that need to be done:
1) Create your task class inside the related subpackage of `com.commencis.checklist.task`
2) Extend `ChecklistTask` class
3) Make your class open so that gradle can create that task
4) Override `taskCode`, `taskDescription` and `taskAction` fields of the `ChecklistTask`
5) Optionally override `taskType` if your task should not work on both application and library projects
6) Add your task's name to the `ChecklistData` class
7) Add your task with its name to `ChecklistTaskContainer` class
8) Make sure that [your changes work](#How to test your changes) as expected and didn't affect the existing functionality