package com.sensehawk.data.mapper

import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.data.test.factory.ProjectFactory
import com.sensehawk.domain.model.Project
import org.junit.Test
import kotlin.test.assertEquals

class ProjectMapperTest {

    private val mapper = ProjectMapper()

    @Test
    fun mapFromEntityMapsData() {
        val entity = ProjectFactory.makeProjectEntity()
        val model = mapper.mapFromEntity(entity)
        assertEqualData(model, entity)
    }

    @Test
    fun mapToEntityMapsData() {
        val model = ProjectFactory.makeProject()
        val entity = mapper.mapToEntity(model)
        assertEqualData(model, entity)
    }

    private fun assertEqualData(project: Project, projectEntity: ProjectEntity) {
        assertEquals(project.id, projectEntity.id)
        assertEquals(project.name, projectEntity.name)
        assertEquals(project.fullName, projectEntity.fullName)
        assertEquals(project.starCount, projectEntity.starCount)
        assertEquals(project.dateCreated, projectEntity.dateCreated)
        assertEquals(project.ownerName, projectEntity.ownerName)
        assertEquals(project.ownerAvatar, projectEntity.ownerAvatar)
        assertEquals(project.isBookmarked, projectEntity.isBookmarked)
    }

}
