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

package com.commencis.checklist.task.build

import com.commencis.checklist.task.ChecklistTask
import com.commencis.checklist.task.ChecklistTaskResult
import com.commencis.checklist.task.constants.ChecklistTaskStatus
import com.commencis.checklist.util.ChecklistData

/**
 * Task that checks whether targetSDK >= ChecklistPluginExtension#targetSDKVersion
 */
open class B6Task : ChecklistTask() {
    override var taskCode: String = ChecklistData.CHECKLIST_TASK_B6
    override var taskDescription = "TargetSDK version should be at least ${checklistPluginExtension.targetSDKVersion}. "

    override var taskAction = {
        val targetSdkVersion = apkAnalyzerHelper.getTargetSdkVersion(project, apkPath)
        this.status = ChecklistTaskStatus.enumerateTaskStatus(
                targetSdkVersion >= checklistPluginExtension.targetSDKVersion)
        val reportMessage = taskDescription + "Found: $targetSdkVersion"

        ChecklistTaskResult(taskCode, status, reportMessage)
    }
}
