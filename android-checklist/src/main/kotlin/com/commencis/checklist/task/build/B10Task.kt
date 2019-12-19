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
 * Task that checks whether Google play services api is included as separate dependencies
 */
open class B10Task : ChecklistTask() {
    override var taskCode: String = ChecklistData.CHECKLIST_TASK_B10
    override var taskDescription = "Google Play Services API should be included as separate dependencies. "

     override var taskAction = {
        val playDependencies = gradleHelper.getPlayDependencies(project)
        this.status = ChecklistTaskStatus.enumerateTaskStatus(playDependencies.isEmpty())
        val reportMessage = taskDescription + "Found: $playDependencies"

        ChecklistTaskResult(taskCode, status, reportMessage)
    }
}