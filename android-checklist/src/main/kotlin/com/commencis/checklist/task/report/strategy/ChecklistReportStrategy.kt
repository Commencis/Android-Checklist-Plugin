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

package com.commencis.checklist.task.report.strategy

import com.commencis.checklist.task.ChecklistTask
import com.commencis.checklist.task.constants.ChecklistTaskStatus
import com.commencis.checklist.task.report.writer.ChecklistReportWriter
import com.commencis.checklist.util.project.ProjectInfo
import java.io.File

abstract class ChecklistReportStrategy {
    abstract val reportType: String
    abstract fun generateReport(projectInfo: ProjectInfo, tasks: List<ChecklistTask>, reportFile: File)

    protected fun generateReportInternal(
            projectInfo: ProjectInfo,
            tasks: List<ChecklistTask>,
            reportFile: File,
            writer: ChecklistReportWriter
    ) {
        writer.insertGeneralFileInfo(projectInfo)
        val failedTasks = tasks.filter { it.result.status == ChecklistTaskStatus.FAIL }.map { it.taskCode }
        val skippedTasks = tasks.filter { it.result.status == ChecklistTaskStatus.SKIP }.map { it.taskCode }
        val allTasks = tasks.map { it.taskCode }
        writer.insertOverallInfo(failedTasks, skippedTasks, allTasks)
        tasks.forEach { task ->
            writer.insertTask(task.result)
        }
        writer.writeReport(reportFile)
    }
}