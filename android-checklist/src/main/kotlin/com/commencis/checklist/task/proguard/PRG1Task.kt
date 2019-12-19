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

package com.commencis.checklist.task.proguard

import com.commencis.checklist.task.ChecklistTask
import com.commencis.checklist.task.ChecklistTaskResult
import com.commencis.checklist.task.constants.ChecklistTaskStatus
import com.commencis.checklist.util.ChecklistData

/**
 * Task that checks whether the apk contains logging classes
 */
open class PRG1Task : ChecklistTask() {
    override var taskCode: String = ChecklistData.CHECKLIST_TASK_PRG1
    override var taskDescription = "Apk should not contain any logging classes. "

    override var taskAction = {
        val classes = apkAnalyzerHelper.getClasses(project, apkPath)
        this.status = ChecklistTaskStatus.enumerateTaskStatus(!classes.contains("android.util.Log"))
        val reportMessage = taskDescription // + "Found: $classes"

        ChecklistTaskResult(taskCode, status, reportMessage)
    }
}
