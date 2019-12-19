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

import com.android.build.gradle.BaseExtension
import com.commencis.checklist.task.ChecklistTask
import com.commencis.checklist.task.report.container.ChecklistReportContainer
import com.commencis.checklist.task.report.container.ChecklistReportContainerImpl
import com.commencis.checklist.util.file.ChecklistFileHelper
import com.commencis.checklist.util.project.ProjectInfo
import groovy.lang.Closure
import groovy.lang.DelegatesTo
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.reporting.Reporting
import org.gradle.api.tasks.TaskAction
import org.gradle.util.ConfigureUtil

/**
 * Main checklist task that depends on all other checklist tasks
 * and generates html report file
 */
open class ChecklistFinalTask : DefaultTask(), Reporting<ChecklistReportContainer> {
    private val reports: ChecklistReportContainer

    lateinit var reportPath: String

    init {
        project.tasks.withType(ChecklistTask::class.java).forEach {
            this.dependsOn(it)
        }
        reports = project.objects.newInstance(ChecklistReportContainerImpl::class.java, this)
        reports.getHtml().isEnabled = true
        reports.getXml().isEnabled = true
    }

    /**
     * Gets results from other tasks and generate report file
     */
    @TaskAction
    fun run() {
        val fileHelper = ChecklistFileHelper()
        reports.enabled.forEach {
            it.destination = fileHelper.getFileWithDirectoryAssurance("$reportPath.${it.name}")
        }
        val reportGenerator = ChecklistReportGenerator(reports.enabled.asMap)
        val allTasks = mutableListOf<ChecklistTask>()
        taskDependencies.getDependencies(this).forEach { task ->
            if (task is ChecklistTask) {
                allTasks.add(task)
            }
        }
        val projectInfo = ProjectInfo(
                project.rootProject.name,
                project.name,
                project.extensions.getByType(BaseExtension::class.java).defaultConfig.versionName
        )
        reportGenerator.generateReports(projectInfo, allTasks)
    }

    override fun getReports(): ChecklistReportContainer {
        return reports
    }

    override fun reports(
            @DelegatesTo(value = ChecklistReportContainer::class, strategy = Closure.DELEGATE_FIRST)
            closure: Closure<Any>): ChecklistReportContainer {
        return reports(ConfigureUtil.configureUsing<ChecklistReportContainer>(closure))
    }

    override fun reports(configureAction: Action<in ChecklistReportContainer>): ChecklistReportContainer {
        configureAction.execute(reports)
        return reports
    }

}
