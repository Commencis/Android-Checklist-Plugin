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
import com.commencis.checklist.util.ChecklistData

/**
 * Task that checks if the version name follows <major>.<minor>.<patch> convention.
 */
open class MAN6Task : ChecklistTask() {
    override var taskCode: String = ChecklistData.CHECKLIST_TASK_MAN6
    override var taskDescription =
            "Version names should follow &lt;major&gt;.&lt;minor&gt;.&lt;patch&gt; convention. "
    override var taskType: MutableList<ChecklistTaskType> = mutableListOf(ChecklistTaskType.LIBRARY)
    //TODO: Check SNAPSHOT on production releases
    override var taskAction = {
        val versionName = apkAnalyzerHelper.getVersionName(project, apkPath, xmlParser)
        this.status = ChecklistTaskStatus.enumerateTaskStatus(
                versionName.matches("[0-9]+\\.[0-9]+\\.[0-9]+".toRegex()))
        val reportMessage = taskDescription + "Found: $versionName"

        ChecklistTaskResult(taskCode, status, reportMessage)
    }
}