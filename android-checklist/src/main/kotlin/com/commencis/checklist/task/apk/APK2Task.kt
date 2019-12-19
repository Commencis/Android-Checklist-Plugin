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

package com.commencis.checklist.task.apk

import com.commencis.checklist.task.ChecklistTask
import com.commencis.checklist.task.ChecklistTaskResult
import com.commencis.checklist.task.constants.ChecklistTaskStatus
import com.commencis.checklist.task.constants.ChecklistTaskType
import com.commencis.checklist.task.constants.OutputSize
import com.commencis.checklist.util.ChecklistData

/**
 * Task that verifies apk size
 */
open class APK2Task : ChecklistTask() {
    override var taskCode: String = ChecklistData.CHECKLIST_TASK_APK2
    override var taskDescription = "Apk size should not exceed maximum size. "
    override var taskType: MutableList<ChecklistTaskType> = mutableListOf(ChecklistTaskType.APPLICATION)

    override var taskAction = {
        val apkSize = apkAnalyzerHelper.getOutputSize(project, apkPath) / 1000 // in Kb
        val maxSize = if (checklistPluginExtension.maxOutputSize == 0) {
            OutputSize.APK_SIZE
        } else {
            checklistPluginExtension.maxOutputSize
        }
        this.status = ChecklistTaskStatus.enumerateTaskStatus(apkSize <= maxSize)
        val reportMessage = taskDescription + "Apk Size: $apkSize kb <br/>" +
                "Allowed maximum size: $maxSize kb"

        ChecklistTaskResult(taskCode, status, reportMessage)
    }
}
