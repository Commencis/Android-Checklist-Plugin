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

package com.commencis.checklist.task.report.writer

import com.commencis.checklist.task.ChecklistTaskResult
import com.commencis.checklist.util.project.ProjectInfo
import com.sun.xml.txw2.output.IndentingXMLStreamWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream
import javax.xml.stream.XMLOutputFactory

class ChecklistXmlWriter : ChecklistReportWriter {
    private var out: OutputStream = ByteArrayOutputStream()

    private val xmlWriter = IndentingXMLStreamWriter(XMLOutputFactory.newFactory().createXMLStreamWriter(out, "UTF-8"))

    init {
        xmlWriter.writeStartDocument("utf-8", "1.0")
        xmlWriter.writeStartElement("checklist")
    }

    override fun insertTask(taskResult: ChecklistTaskResult) {
        xmlWriter.writeStartElement("task")
        xmlWriter.writeStartElement("code")
        xmlWriter.writeCharacters(taskResult.taskCode)
        xmlWriter.writeEndElement()
        xmlWriter.writeStartElement("status")
        xmlWriter.writeCharacters(taskResult.status.name)
        xmlWriter.writeEndElement()
        xmlWriter.writeStartElement("description")
        xmlWriter.writeCharacters(taskResult.taskDescription)
        xmlWriter.writeEndElement()
        xmlWriter.writeEndElement()
    }

    override fun insertOverallInfo(failedTasks: List<String>, skippedTasks: List<String>, allTasks: List<String>) {
        xmlWriter.writeStartElement("overallInfo")
        xmlWriter.writeStartElement("taskCount")
        xmlWriter.writeCharacters(allTasks.size.toString())
        xmlWriter.writeEndElement()
        xmlWriter.writeStartElement("failedTaskCount")
        xmlWriter.writeCharacters(failedTasks.size.toString())
        xmlWriter.writeEndElement()
        xmlWriter.writeStartElement("failedTasks")
        xmlWriter.writeCharacters(failedTasks.joinToString(", "))
        xmlWriter.writeEndElement()
        xmlWriter.writeStartElement("skippedTaskCount")
        xmlWriter.writeCharacters(skippedTasks.size.toString())
        xmlWriter.writeEndElement()
        xmlWriter.writeStartElement("skippedTasks")
        xmlWriter.writeCharacters(skippedTasks.joinToString(", "))
        xmlWriter.writeEndElement()
        xmlWriter.writeEndElement()
        xmlWriter.writeStartElement("tasks")
    }

    override fun insertGeneralFileInfo(projectInfo: ProjectInfo) {
        xmlWriter.writeEmptyElement("project")
        xmlWriter.writeAttribute("projectName", projectInfo.name)
        xmlWriter.writeAttribute("moduleName", projectInfo.moduleName)
        xmlWriter.writeAttribute(
                "versionName",
                projectInfo.versionName.toString() // Since versionName is nullable, toString() is used
        )
    }

    override fun writeReport(file: File) {
        xmlWriter.writeEndElement()
        xmlWriter.writeEndElement()
        xmlWriter.writeEndDocument()
        file.writeText(out.toString())
        println("Checklist XML Report is generated at ${file.absolutePath}")
    }


}