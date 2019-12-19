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

import com.commencis.checklist.util.project.type.IProjectTypeProvider
import com.commencis.checklist.util.project.type.ProjectType
import com.commencis.testUtils.getActionField
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ObjectConfigurationAction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class ChecklistBasePluginTest {

    @Mock
    private lateinit var mockProjectTypeProvider: IProjectTypeProvider

    private lateinit var checklistBasePlugin: ChecklistBasePlugin

    @BeforeEach
    fun init() {
        initMocks(this)
        checklistBasePlugin = ChecklistBasePlugin(mockProjectTypeProvider)
    }

    @Test
    fun `When ChecklistBasePlugin applied to an Application Project, it should apply ApplicationChecklistPlugin`() {
        val project: Project = mock()
        whenever(mockProjectTypeProvider.getProjectType(any())).thenReturn(ProjectType.APPLICATION)
        val action = getAction(project)
        assertThat(action).isEqualTo(ApplicationChecklistPlugin::class.java)
    }

    @Test
    fun `When ChecklistBasePlugin applied to an Library Project, it should apply LibraryChecklistPlugin`() {
        val project: Project = mock()
        whenever(mockProjectTypeProvider.getProjectType(any())).thenReturn(ProjectType.LIBRARY)
        val action = getAction(project)
        assertThat(action).isEqualTo(LibraryChecklistPlugin::class.java)
    }

    @Test
    fun `When ChecklistBasePlugin applied to an Feature Project, it should apply FeatureChecklistPlugin`() {
        val project: Project = mock()
        whenever(mockProjectTypeProvider.getProjectType(any())).thenReturn(ProjectType.FEATURE)
        val action = getAction(project)
        assertThat(action).isEqualTo(FeatureChecklistPlugin::class.java)
    }

    private fun getAction(project: Project): Any{
        checklistBasePlugin.apply(project)
        val clazz = object : TypeToken<Action<ObjectConfigurationAction>>() {}.rawType!! as Class<Action<ObjectConfigurationAction>>
        val argumentCaptor: ArgumentCaptor<Action<ObjectConfigurationAction>> = ArgumentCaptor.forClass(clazz)
        verify(project).apply(argumentCaptor.capture())
        return getActionField("\$clazz", argumentCaptor)
    }

}