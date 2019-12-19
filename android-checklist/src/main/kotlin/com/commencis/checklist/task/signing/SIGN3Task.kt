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
 * Task that checks whether the apk contains release keystore
 */
open class SIGN3Task : ChecklistTask() {
    override var taskCode: String = ChecklistData.CHECKLIST_TASK_SIGN3
    override var taskDescription = "Apk should not contain release keystore files. "

    override var taskAction = {
        val files = apkAnalyzerHelper.getFiles(project, apkPath)
        val build =  buildTypeProvider.getBuildType(project, AndroidBuildType.RELEASE)
        val signingConfig = build.signingConfig
        this.status = ChecklistTaskStatus.enumerateTaskStatus(signingConfig == null ||
                signingConfig.storeFile == null ||
                !files.contains(signingConfig.storeFile.name))
        val reportMessage = taskDescription // + "Found: $files"

        ChecklistTaskResult(taskCode, status, reportMessage)
    }
}