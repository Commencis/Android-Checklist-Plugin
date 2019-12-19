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
import com.commencis.checklist.task.constants.ChecklistTaskStatus
import com.commencis.checklist.util.project.ProjectInfo
import java.io.File

class ChecklistHtmlWriter : ChecklistReportWriter {

    private var html = """
        <!DOCTYPE html>
        <html>
            <head>
                <link 
                    rel="stylesheet" 
                    href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
                    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
                    crossorigin="anonymous"/>
                <link 
                    href="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.19/css/dataTables.bootstrap4.min.css"
                    rel="stylesheet"/>
                <script 
                    src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" 
                    integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" 
                    crossorigin="anonymous"></script>
                <title>Checklist</title>
            </head>
            <body>
                <div class="container">
            """.trimIndent()

    private var table = """
        <table id="taskTable" class="table table-hover" cellspacing="0"> 
            <colgroup>
                <col span="1" style="width: 20%;">
                <col span="1" style="width: 20%;">
                <col span="1" style="width: 60%;">
            </colgroup>
            <thead>
                <tr class="table-active">
                    <th>Task</th>
                    <th>Status</th>
                    <th>Description</th>
                </tr>
            </thead>
                """.trimIndent()

    private val tableEnd = "</table>"

    private var fileInfo = """
       <div>
            <h1 class="text-left">Checklist Plugin Report</h1>
            """.trimIndent()

    private val fileInfoEnd = "</div>"

    private var overallInfo = "<div class=\"card-deck\">"

    private val overallInfoEnd = "</div>"

    private val fileEnd = """
                </div>
            </body>
            <script 
            src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
            <script 
            src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.19/js/jquery.dataTables.min.js"></script>
            <script 
            src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.19/js/dataTables.bootstrap4.min.js"></script>
            <script>
                $(document).ready(function() {
                  $('#taskTable').DataTable({
                   "lengthMenu": [ [-1, 10, 25, 50], ["All", 10, 25, 50] ]  });
                });
            </script>
        </html>
        """.trimIndent()

    override fun insertTask(taskResult: ChecklistTaskResult) {
        val statusClass = when (taskResult.status) {
            ChecklistTaskStatus.SUCCESS -> "<tr class=\"table-success\">"
            ChecklistTaskStatus.FAIL -> "<tr class=\"table-danger\">"
            ChecklistTaskStatus.SKIP -> "<tr class=\"table-warning\">"
            ChecklistTaskStatus.INFO -> "<tr class=\"table-info\">"
        }
        val tableRow = """
            $statusClass    
                <td>${taskResult.taskCode}</td>
                <td>${taskResult.status}</td>
                <td>${taskResult.taskDescription}</td>
            </tr>
          """.trimIndent()

        table += tableRow
    }

    override fun insertOverallInfo(
            failedTasks: List<String>,
            skippedTasks: List<String>,
            allTasks: List<String>
    ) {
        val totalTaskRow = generateOverallTableRow("primary", "Total Task Count", allTasks.size.toString())
        val failedTaskCountRow = generateOverallTableRow("danger", "Failed Task Count", failedTasks.size.toString())
        val failedTaskRow = generateOverallTableRow("danger", "Failed Tasks", failedTasks.joinToString("</br>"))
        val skippedTaskCountRow = generateOverallTableRow("warning", "Skipped Task Count", skippedTasks.size.toString())
        val skippedTasksRow = generateOverallTableRow("warning", "Skipped Tasks", skippedTasks.joinToString("</br>"))

        overallInfo += (totalTaskRow + failedTaskCountRow + failedTaskRow + skippedTaskCountRow + skippedTasksRow)
    }

    private fun generateOverallTableRow(color: String, header: String, body: String) = """
            <div class="card text-white bg-$color my-3">
              <div class="card-header text-center">$header</div>
              <div class="card-body">
                <h5 class="card-title text-center">$body</h5>
              </div>
            </div>
            """.trimIndent()

    override fun insertGeneralFileInfo(projectInfo: ProjectInfo) {
        val projectHtml = """
            <h3 class="text-left">${projectInfo.name}</h3>
            <h4 class="text-right">Module: ${projectInfo.moduleName}</h4>
            <h5 class="text-right">Version: ${projectInfo.versionName}</h5>
        """.trimIndent()
        fileInfo += projectHtml
    }

    private fun getFileContent(): String =
            html + fileInfo + fileInfoEnd + overallInfo + overallInfoEnd + table + tableEnd + fileEnd

    override fun writeReport(file: File) {
        file.writeText(getFileContent())
        println("Checklist HTML Report is generated at ${file.absolutePath}")
    }
}
