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

package com.commencis.checklist.task.report

import com.commencis.checklist.task.ChecklistTask
import com.commencis.checklist.task.report.strategy.ChecklistHTMLReportStrategy
import com.commencis.checklist.task.report.strategy.ChecklistReportStrategy
import com.commencis.checklist.task.report.strategy.ChecklistXMLReportStrategy
import com.commencis.checklist.util.project.ProjectInfo
import org.gradle.api.reporting.SingleFileReport

class ChecklistReportGenerator(private val enabledReports: Map<String, SingleFileReport>) {
    private val reportStrategies: List<ChecklistReportStrategy> = listOf(
            ChecklistHTMLReportStrategy(),
            ChecklistXMLReportStrategy())

    fun generateReports(projectInfo: ProjectInfo, tasks: List<ChecklistTask>) {
        reportStrategies.forEach {
            if (it.reportType in enabledReports) {
                val report = enabledReports[it.reportType]
                it.generateReport(projectInfo, tasks, report!!.destination)
            }
        }
    }
}