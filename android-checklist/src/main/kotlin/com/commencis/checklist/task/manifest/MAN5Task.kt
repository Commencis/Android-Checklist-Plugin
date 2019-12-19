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

package com.commencis.checklist.task.manifest

import com.commencis.checklist.task.ChecklistTask
import com.commencis.checklist.task.ChecklistTaskResult
import com.commencis.checklist.task.constants.ChecklistTaskStatus
import com.commencis.checklist.task.constants.ChecklistTaskType
import com.commencis.checklist.task.constants.InstallLocationValue
import com.commencis.checklist.util.ChecklistData

/**
 * Task that verifies installLocation attribute on manifest
 */
open class MAN5Task : ChecklistTask() {
    override var taskCode: String = ChecklistData.CHECKLIST_TASK_MAN5
    override var taskDescription =
            "android:installLocation should not be set preferExternal. "
    override var taskType: MutableList<ChecklistTaskType> = mutableListOf(ChecklistTaskType.APPLICATION)

    override var taskAction = {
        val installLocation = apkAnalyzerHelper.getInstallLocation(project, apkPath, xmlParser)
        this.status = ChecklistTaskStatus.enumerateTaskStatus(installLocation == -1 ||
                installLocation in checklistPluginExtension.allowedInstallLocations.map { it.value })
        val reportMessage = taskDescription +
                "Found: ${InstallLocationValue.getInstallLocationValueFromIndex(installLocation).installLocation}"

        ChecklistTaskResult(taskCode, status, reportMessage)
    }
}