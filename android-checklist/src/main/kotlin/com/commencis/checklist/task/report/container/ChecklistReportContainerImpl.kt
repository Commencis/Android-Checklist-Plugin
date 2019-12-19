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

package com.commencis.checklist.task.report.container

import org.gradle.api.Task
import org.gradle.api.reporting.SingleFileReport
import org.gradle.api.reporting.internal.CustomizableHtmlReportImpl
import org.gradle.api.reporting.internal.TaskGeneratedSingleFileReport
import org.gradle.api.reporting.internal.TaskReportContainer
import javax.inject.Inject

const val htmlReportType = "html"
const val xmlReportType = "xml"

open class ChecklistReportContainerImpl
@Inject
constructor(task: Task) :
        TaskReportContainer<SingleFileReport>(SingleFileReport::class.java, task),
        ChecklistReportContainer {

    init {
        add(CustomizableHtmlReportImpl::class.java, htmlReportType, task)
        add(TaskGeneratedSingleFileReport::class.java, xmlReportType, task)
    }

    override fun getHtml(): SingleFileReport = getByName(htmlReportType)

    override fun getXml(): SingleFileReport = getByName(xmlReportType)

}
