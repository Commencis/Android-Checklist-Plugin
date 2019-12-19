# Checklist Gradle Plugin

## What is Checklist Plugin?
Checklist Plugin is a Gradle plugin for Android projects to verify output(apk, aar, etc.) before release.
It checks several items that are common for most android projects and generates an HTML report with the 
results of these tasks.

## Features
Checklist Plugin currently has the following tasks:

| Task | Description |
| --- | --- |
| checklistAPK2| verifies apk size
| checklistB1| checks whether res configs include only the necessary resources 
| checklistB4| checks whether appId starts with some given convention
| checklistB5| checks whether minSDK <= some given version number(defaults to 16)
| checklistB6| checks whether targetSDK >= some given version number(defaults to 29)
| checklistB7| checks whether compileSDK >= some given version number(defaults to 29)
| checklistB8| checks whether the dependencies have specific version numbers instead of -SNAPSHOT and +
| checklistB9| checks whether apk/aar is debuggable
| checklistB10| checks whether Google play services api is included as separate dependencies
| checklistDIST2| verifies aar size
| checklistGEN1| checks whether git branch name has the given prefix
| checklistGEN2| reminds to make sure that basic functionality of the app is fully working
| checklistGEN3| reminds to make sure that the app doesn't crash and works as expected
| checklistGEN4| verifies permissions, locales and densities
| checklistGEN5| checks that unit tests ran and did not fail
| checklistMAN1| shows current version code to check whether it is incremented
| checklistMAN2| shows version name to check if it is updated
| checklistMAN3| checks if the version name follows <major>.<minor>.<patch>.<buildNumber> convention
| checklistMAN5| verifies installLocation attribute on manifest
| checklistMAN6| checks if the version name follows <major>.<minor>.<patch> convention
| checklistPERM2| shows permissions to check that if an unnecessary permission is used
| checklistPERM3| checks whether android:required set false in the manifest.
| checklistPRG1| checks whether apk contains any logging classes
| checklistPRG3| checks whether proguard files are set through proguardFiles property
| checklistPRG6| checks whether minifyEnabled and shrinkResources are set true
| checklistSIGN1| checks whether keystore file is in project folder
| checklistSIGN3| checks whether the apk contains release keystore
| checklistSIGN4| checks whether storePassword, keyAlias, keyPassword are set in the build.gradle file
| checklist| Main task that generates HTML report

In addition to the predefined tasks, one can add custom tasks to the list. Refer to [Adding Custom Task](#Adding Custom Task)

## Setup
1. Add `classpath 'com.commencis.checklist:checklist:$checklistVersion'` to your project level `build.gradle` file.
2. Add `apply plugin: 'com.commencis.checklist.plugin'` to your module level `build.gradle` file.
3. If you have product flavors, you need to set the flavor you want to run checklist inside the checklist extension block.
````groovy
checklist{
    flavorName = "myFlavor"
}
````
- Don't forget to consider dimensions as well. For example, if you have
```groovy
   flavorDimensions "dim1", "dim2"
    productFlavors {
        flavor1 {
            dimension "dim1"
        }
        flavor2{
            dimension "dim2"
        }
    }
```
on your build.gradle file, then you need to set `flavorName` parameter as
````groovy
checklist{
    flavorName = "flavor1Flavor2"
}
````

## Basic Usage
To use Checklist Plugin you can simply run 
```shell script
./gradlew checklist
```
It will automatically generate an HTML report to `<module>/build/checklist/report.html` file.

## Customization
### Adding Custom Task
To add a custom task for your project, you should:
1. Create a task of type `com.commencis.checklist.task.ChecklistTask`
2. Override `taskCode`, `taskDescription`, `taskType`(optionally) and `taskAction`
3. Don't forget to return `com.commencis.checklist.task.ChecklistTaskResult` instance at the end of `taskAction`

##### Example

```groovy
task example(type: ChecklistTask){
    taskCode = "Example"
    taskDescription = "Description"

    taskAction {
        println("Some action!")
        new ChecklistTaskResult(taskCode, ChecklistTaskStatus.SUCCESS, taskDescription)
    }
}
```

### Configuration
You can change the values of many tasks. To do this you should create a block for ChecklistPluginExtension 
on your `build.gradle` file.

##### Example

```groovy
checklist{
    minSDKVersion = 21
    applicationIdConvention = "com.mycompany.myapp"
}
```

##### Configurable Variables

| Variable | Type | Description | Default Value 
|---|---|---|---|
| minSDKVersion | Integer | Indicates the minimum sdk version of the project | 16 |
| targetSDKVersion | Integer | Indicates the target sdk version of the project | 29 |
| compileSDKVersion | Integer | Indicates the compile sdk version of the project | 29 |
| applicationIdConvention | String | Indicates the applicationId prefix of the project | "" |
| resConfigs | List<String> | Indicates the resource configs used in the project | ["en", "tr"] |
| branchPrefix | String | Indicates the git branch prefix | "version/" |
| allowedInstallLocations | List<InstallLocationValue> | Indicates the allowed android:installLocation values | [InstallLocationValue.AUTO, InstallLocationValue.INTERNAL_ONLY] |


### Skipping Tasks
You can skip default tasks you don't want to execute. They will be present on the generated HTML report 
as skipped.

To skip tasks, you should set `skippedTasks : List<String>` variable of the `ChecklistPluginExtension`.

##### Example
```groovy
checklist{
    skippedTasks = ["checklistB5", "checklistB6"]
}
```

## Requirements
To use Checklist Plugin you need at least:
- Android Gradle Plugin 3.3.0
- Gradle Wrapper 4.10.1

## Releases

## Contributing
Refer to [CONTRIBUTING.md](CONTRIBUTING.md)
