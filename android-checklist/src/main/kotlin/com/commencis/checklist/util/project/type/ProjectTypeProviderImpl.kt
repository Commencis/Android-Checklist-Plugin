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

package com.commencis.checklist.util.project.type

import com.commencis.checklist.util.project.resolver.IProjectResolverProvider
import com.commencis.checklist.util.project.resolver.ProjectResolverProviderImpl
import org.gradle.api.Project

class ProjectTypeProviderImpl : IProjectTypeProvider {
    override val projectResolverProvider: IProjectResolverProvider
        get() = ProjectResolverProviderImpl()

    override fun getProjectType(project: Project): ProjectType {
        return when {
            projectResolverProvider.androidProjectResolver.isAndroidApplication(project.plugins) ->
                ProjectType.APPLICATION
            projectResolverProvider.androidProjectResolver.isAndroidLibrary(project.plugins) ->
                ProjectType.LIBRARY
            projectResolverProvider.androidProjectResolver.isAndroidFeature(project.plugins) ->
                ProjectType.FEATURE
            else ->
                ProjectType.APPLICATION
        }
    }
}