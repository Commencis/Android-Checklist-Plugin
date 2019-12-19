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

package com.commencis.checklist

import com.commencis.checklist.extensions.applyPlugin
import com.commencis.checklist.util.project.type.IProjectTypeProvider
import com.commencis.checklist.util.project.type.ProjectType
import com.commencis.checklist.util.project.type.ProjectTypeProviderImpl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.impldep.com.google.api.client.repackaged.com.google.common.annotations.VisibleForTesting
import javax.inject.Inject

open class ChecklistBasePlugin : Plugin<Project> {

    protected val projectTypeProvider: IProjectTypeProvider

    @Inject
    constructor() : this(
            ProjectTypeProviderImpl()
    )

    @VisibleForTesting
    constructor(projectTypeProvider: IProjectTypeProvider) {
        this.projectTypeProvider = projectTypeProvider
    }

    override fun apply(project: Project) {
        when (projectTypeProvider.getProjectType(project)) {
            ProjectType.APPLICATION -> project.applyPlugin(ApplicationChecklistPlugin::class.java)
            ProjectType.LIBRARY -> project.applyPlugin(LibraryChecklistPlugin::class.java)
            ProjectType.FEATURE -> project.applyPlugin(FeatureChecklistPlugin::class.java)
        }
    }

}