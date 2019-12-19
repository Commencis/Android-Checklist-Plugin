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
import com.commencis.checklist.util.plugin.android.AndroidBuildType

/**
 * Task that checks whether minifyEnabled and shrinkResources are set true. for release builds.
 */
open class PRG6Task : ChecklistTask() {
    override var taskCode: String = ChecklistData.CHECKLIST_TASK_PRG6
    override var taskDescription = "minifyEnabled and shrinkResources should be set to true. "

    override var taskAction = {
        val build = buildTypeProvider.getBuildType(project, AndroidBuildType.RELEASE)
        val isMinified = build.isMinifyEnabled && build.isShrinkResources
        this.status = ChecklistTaskStatus.enumerateTaskStatus(isMinified)
        val reportMessage = taskDescription +
                "Found: MinifyEnabled:${build.isMinifyEnabled}\n" +
                "ShrinkResources: ${build.isShrinkResources}"

        ChecklistTaskResult(taskCode, status, reportMessage)
    }
}