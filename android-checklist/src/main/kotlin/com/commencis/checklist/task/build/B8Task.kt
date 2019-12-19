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
 * Task that checks whether the dependencies have specific version numbers instead of -SNAPSHOT and +
 */
open class B8Task : ChecklistTask() {
    override var taskCode: String = ChecklistData.CHECKLIST_TASK_B8
    override var taskDescription = "Dependencies should use specific version number, not '+' or '-SNAPSHOT'."

     override var taskAction = {
        val dependencyVersions = gradleHelper.getApkDependencies(project)
        this.status = ChecklistTaskStatus.enumerateTaskStatus(!dependencyVersions.contains(Regex("-SNAPSHOT|:\\+")))
        val reportMessage = taskDescription //TODO + "Found: ..."

        ChecklistTaskResult(taskCode, status, reportMessage)
    }
}
