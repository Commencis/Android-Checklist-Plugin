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

package com.commencis.checklist.task

import com.commencis.checklist.ChecklistPluginExtension
import com.commencis.checklist.task.constants.ChecklistTaskStatus
import com.commencis.checklist.task.constants.ChecklistTaskType
import com.commencis.checklist.util.ChecklistData
import com.commencis.checklist.util.command.AaptHelper
import com.commencis.checklist.util.command.ApkAnalyzerHelper
import com.commencis.checklist.util.command.GitHelper
import com.commencis.checklist.util.command.GradleHelper
import com.commencis.checklist.util.file.XmlParser
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

/**
 * Base class for checklist tasks
 */
open class ChecklistTask(provider: ChecklistTaskDependencyProvider) : DefaultTask() {
    open var taskCode: String = "DefaultTaskCode"
    open var taskDescription: String = "You probably forgot to override task fields!"
    open var taskType: MutableList<ChecklistTaskType> =
            mutableListOf(ChecklistTaskType.APPLICATION, ChecklistTaskType.LIBRARY)
    open var taskAction: () -> (ChecklistTaskResult) = {
        ChecklistTaskResult(taskCode, status, taskDescription)
    }

    lateinit var result: ChecklistTaskResult
    lateinit var apkPath: String

    protected var status = ChecklistTaskStatus.FAIL

    protected val apkAnalyzerHelper: ApkAnalyzerHelper = provider.apkAnalyzerHelper
    protected val aaptHelper: AaptHelper = provider.aaptHelper
    protected val gradleHelper: GradleHelper = provider.gradleHelper
    protected val gitHelper: GitHelper = provider.gitHelper
    protected val xmlParser: XmlParser = provider.xmlParser
    protected val buildTypeProvider = provider.buildTypeProvider

    protected val checklistPluginExtension: ChecklistPluginExtension =
            project.extensions.getByType(ChecklistPluginExtension::class.java)

    @Inject
    constructor() : this(
            ChecklistTaskDependencyProviderImpl()
    )

    @TaskAction
    fun run() {
        if (apkPath.isBlank()) {
            throw IllegalStateException("Checklist couldn't find the apk/aar! " +
                    "Please be sure that you have set the correct product flavor.")
        }

        this.result = if (status == ChecklistTaskStatus.SKIP) {
            ChecklistTaskResult(taskCode, status, taskDescription)
        } else {
            taskAction.invoke()
        }
    }

    fun skipTask() {
        this.status = ChecklistTaskStatus.SKIP
    }
}
