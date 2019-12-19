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

package com.commencis.checklist.task.constants

import com.commencis.checklist.task.apk.APK2Task
import com.commencis.checklist.task.build.B10Task
import com.commencis.checklist.task.build.B1Task
import com.commencis.checklist.task.build.B4Task
import com.commencis.checklist.task.build.B5Task
import com.commencis.checklist.task.build.B6Task
import com.commencis.checklist.task.build.B7Task
import com.commencis.checklist.task.build.B8Task
import com.commencis.checklist.task.build.B9Task
import com.commencis.checklist.task.distribution.DIST2Task
import com.commencis.checklist.task.general.GEN1Task
import com.commencis.checklist.task.general.GEN2Task
import com.commencis.checklist.task.general.GEN3Task
import com.commencis.checklist.task.general.GEN4Task
import com.commencis.checklist.task.general.GEN5Task
import com.commencis.checklist.task.manifest.MAN1Task
import com.commencis.checklist.task.manifest.MAN2Task
import com.commencis.checklist.task.manifest.MAN3Task
import com.commencis.checklist.task.manifest.MAN5Task
import com.commencis.checklist.task.manifest.MAN6Task
import com.commencis.checklist.task.permissions.PERM2Task
import com.commencis.checklist.task.permissions.PERM3Task
import com.commencis.checklist.task.proguard.PRG1Task
import com.commencis.checklist.task.proguard.PRG3Task
import com.commencis.checklist.task.proguard.PRG6Task
import com.commencis.checklist.task.signing.SIGN1Task
import com.commencis.checklist.task.signing.SIGN3Task
import com.commencis.checklist.task.signing.SIGN4Task
import com.commencis.checklist.util.ChecklistData

object ChecklistTaskContainer {

    val tasks = mapOf(
            ChecklistData.CHECKLIST_TASK_APK2 to APK2Task::class.java,
            ChecklistData.CHECKLIST_TASK_B1 to B1Task::class.java,
            ChecklistData.CHECKLIST_TASK_B4 to B4Task::class.java,
            ChecklistData.CHECKLIST_TASK_B5 to B5Task::class.java,
            ChecklistData.CHECKLIST_TASK_B6 to B6Task::class.java,
            ChecklistData.CHECKLIST_TASK_B7 to B7Task::class.java,
            ChecklistData.CHECKLIST_TASK_B8 to B8Task::class.java,
            ChecklistData.CHECKLIST_TASK_B9 to B9Task::class.java,
            ChecklistData.CHECKLIST_TASK_B10 to B10Task::class.java,
            ChecklistData.CHECKLIST_TASK_DIST2 to DIST2Task::class.java,
            ChecklistData.CHECKLIST_TASK_GEN1 to GEN1Task::class.java,
            ChecklistData.CHECKLIST_TASK_GEN2 to GEN2Task::class.java,
            ChecklistData.CHECKLIST_TASK_GEN3 to GEN3Task::class.java,
            ChecklistData.CHECKLIST_TASK_GEN4 to GEN4Task::class.java,
            ChecklistData.CHECKLIST_TASK_GEN5 to GEN5Task::class.java,
            ChecklistData.CHECKLIST_TASK_PRG1 to PRG1Task::class.java,
            ChecklistData.CHECKLIST_TASK_PRG3 to PRG3Task::class.java,
            ChecklistData.CHECKLIST_TASK_PRG6 to PRG6Task::class.java,
            ChecklistData.CHECKLIST_TASK_PERM2 to PERM2Task::class.java,
            ChecklistData.CHECKLIST_TASK_PERM3 to PERM3Task::class.java,
            ChecklistData.CHECKLIST_TASK_SIGN1 to SIGN1Task::class.java,
            ChecklistData.CHECKLIST_TASK_SIGN3 to SIGN3Task::class.java,
            ChecklistData.CHECKLIST_TASK_SIGN4 to SIGN4Task::class.java,
            ChecklistData.CHECKLIST_TASK_MAN1 to MAN1Task::class.java,
            ChecklistData.CHECKLIST_TASK_MAN2 to MAN2Task::class.java,
            ChecklistData.CHECKLIST_TASK_MAN3 to MAN3Task::class.java,
            ChecklistData.CHECKLIST_TASK_MAN5 to MAN5Task::class.java,
            ChecklistData.CHECKLIST_TASK_MAN6 to MAN6Task::class.java
    )

}