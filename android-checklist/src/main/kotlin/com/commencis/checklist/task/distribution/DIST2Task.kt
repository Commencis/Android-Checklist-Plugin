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

package com.commencis.checklist.task.distribution

import com.commencis.checklist.task.ChecklistTask
import com.commencis.checklist.task.ChecklistTaskResult
import com.commencis.checklist.task.constants.ChecklistTaskStatus
import com.commencis.checklist.task.constants.ChecklistTaskType
import com.commencis.checklist.task.constants.OutputSize
import com.commencis.checklist.util.ChecklistData

/**
 * Task that verifies aar size
 */
open class DIST2Task : ChecklistTask() {
    override var taskCode: String = ChecklistData.CHECKLIST_TASK_DIST2
    override var taskDescription = "Aar size should not exceed maximum size. "
    override var taskType: MutableList<ChecklistTaskType> = mutableListOf(ChecklistTaskType.LIBRARY)

    override var taskAction = {
        val aarSize = apkAnalyzerHelper.getOutputSize(project, apkPath) / 1000 // in Kb
        val maxSize = if (checklistPluginExtension.maxOutputSize == 0) {
            OutputSize.AAR_SIZE
        } else {
            checklistPluginExtension.maxOutputSize
        }
        this.status = ChecklistTaskStatus.enumerateTaskStatus(aarSize <= maxSize)
        val reportMessage = taskDescription + "Aar Size: $aarSize kb <br/>" +
                "Allowed maximum size: $maxSize kb"

        ChecklistTaskResult(taskCode, status, reportMessage)
    }
}
