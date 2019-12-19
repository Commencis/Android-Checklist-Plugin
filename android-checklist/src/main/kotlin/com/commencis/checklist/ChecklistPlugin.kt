/*
 * Copyright (C) 2019 Commencis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.commencis.checklist

import com.android.build.gradle.AppExtension
import com.android.build.gradle.FeatureExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.tasks.factory.registerTask
import com.commencis.checklist.task.ChecklistTask
import com.commencis.checklist.task.constants.ChecklistTaskContainer
import com.commencis.checklist.task.constants.ChecklistTaskType
import com.commencis.checklist.task.report.ChecklistFinalTask
import com.commencis.checklist.util.ChecklistData
import com.commencis.checklist.util.plugin.android.AndroidBuildType
import com.commencis.checklist.util.plugin.android.AndroidPluginHelperImpl
import com.commencis.checklist.util.plugin.android.AndroidPluginOwner
import com.commencis.checklist.util.plugin.android.IAndroidPluginHelper
import com.commencis.checklist.util.project.type.ProjectType
import org.gradle.api.Project
import java.io.File

/**
 * Plugin to perform apk validation checks
 */
sealed class ChecklistPlugin : ChecklistBasePlugin(), AndroidPluginOwner {

    override val androidPluginHelper: IAndroidPluginHelper
        get() = AndroidPluginHelperImpl()

    private lateinit var checklistPluginExtension: ChecklistPluginExtension

    override fun apply(project: Project) {
        this.checklistPluginExtension = project.extensions.create(
                ChecklistData.CHECKLIST_EXTENSION_NAME,
                ChecklistPluginExtension::class.java
        )

        var apkPath = ""
        var assembleTask = ""
        getVariants(project).all { variant ->
            if (variant.buildType.name.toLowerCase() == AndroidBuildType.RELEASE.buildTypeName &&
                    variant.flavorName == checklistPluginExtension.flavorName) {
                variant.outputs.all {
                    assembleTask = variant.assembleProvider.name
                    apkPath = it.outputFile.absolutePath
                }
            }
        }

        val projectTaskType = getTaskType(project)

        if (checklistPluginExtension.reportPath.isBlank()) {
            checklistPluginExtension.reportPath =
                    "${project.buildDir}${File.separator}checklist${File.separator}" +
                            ChecklistData.CHECKLIST_REPORT_NAME
        }

        project.afterEvaluate {
            if (apkPath.isBlank()) {
                project.logger.error("Checklist couldn't find the apk/aar! " +
                        "Please be sure that you have set the correct product flavor.")
            }
            // Register predefined tasks
            ChecklistTaskContainer.tasks.forEach { task ->
                project.tasks.registerTask(ChecklistData.CHECKLIST_TASK_PREFIX + task.key, task.value)
            }

            // Configure all tasks(predefined and custom)
            project.tasks.withType(ChecklistTask::class.java).forEach {
                it.apkPath = apkPath
                if (it.name in checklistPluginExtension.skippedTasks || // If skipped by host app
                        projectTaskType !in it.taskType) { // If type of the task is not compatible with project type
                    it.skipTask()
                }
                if (assembleTask.isNotBlank()) {
                    it.mustRunAfter(assembleTask)
                }
            }

            project.tasks.register(ChecklistData.CHECKLIST_TASK_REPORT, ChecklistFinalTask::class.java) {
                if (checklistPluginExtension.reportPath.isNotBlank()) {
                    it.reportPath = checklistPluginExtension.reportPath
                }

                if (assembleTask.isNotBlank()) {
                    it.dependsOn(assembleTask)
                }
            }
        }
    }

    private fun getTaskType(project: Project): ChecklistTaskType = when (projectTypeProvider.getProjectType(project)) {
        ProjectType.APPLICATION -> ChecklistTaskType.APPLICATION
        ProjectType.LIBRARY -> ChecklistTaskType.LIBRARY
        ProjectType.FEATURE -> ChecklistTaskType.APPLICATION
    }
}

class LibraryChecklistPlugin : ChecklistPlugin() {
    override fun getAndroidExtension(project: Project) = super.getAndroidExtension(project) as LibraryExtension
    override fun getVariants(project: Project) = getAndroidExtension(project).libraryVariants!!
}

class FeatureChecklistPlugin : ChecklistPlugin() {
    override fun getAndroidExtension(project: Project) = super.getAndroidExtension(project) as FeatureExtension
    override fun getVariants(project: Project) = getAndroidExtension(project).featureVariants!!
}

class ApplicationChecklistPlugin : ChecklistPlugin() {
    override fun getAndroidExtension(project: Project) = super.getAndroidExtension(project) as AppExtension
    override fun getVariants(project: Project) = getAndroidExtension(project).applicationVariants!!
}
