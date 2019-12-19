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

package com.commencis.checklist.task.signing

import com.android.build.gradle.BaseExtension
import com.commencis.checklist.task.ChecklistTask
import com.commencis.checklist.task.ChecklistTaskResult
import com.commencis.checklist.task.constants.ChecklistTaskStatus
import com.commencis.checklist.util.ChecklistData
import com.commencis.checklist.util.plugin.android.AndroidBuildType

/**
 * Task that checks whether keystore file is in project folder.
 */
open class SIGN1Task : ChecklistTask() {
    override var taskCode: String = ChecklistData.CHECKLIST_TASK_SIGN1
    override var taskDescription = "Keystore file should be in project folder. "

    override var taskAction = {
        val build =  buildTypeProvider.getBuildType(project, AndroidBuildType.RELEASE)
        val signingConfig = build.signingConfig
        this.status = ChecklistTaskStatus.enumerateTaskStatus(signingConfig != null &&
                signingConfig.storeFile != null &&
                signingConfig.storeFile.absolutePath.startsWith(project.projectDir.absolutePath))
        val reportMessage = taskDescription +
                "Found: " + when {
            signingConfig == null -> {
                "No signing config found"
            }
            signingConfig.storeFile == null -> {
                "No store file found"
            }
            else -> {
                signingConfig.storeFile
            }
        }

        ChecklistTaskResult(taskCode, status, reportMessage)
    }
}