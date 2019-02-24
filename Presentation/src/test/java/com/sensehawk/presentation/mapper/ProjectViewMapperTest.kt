package com.sensehawk.presentation.mapper

import com.sensehawk.presentation.test.factory.ProjectFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectViewMapperTest {

    private val mapper = ProjectViewMapper()

    @Test
    fun mapToViewMapsData() {
        val domain = ProjectFactory.makeProject()
        val view = mapper.mapToView(domain)
        assertEquals(domain.id, view.id)
        assertEquals(domain.name, view.name)
        assertEquals(domain.fullName, view.fullName)
        assertEquals(domain.starCount, view.starCount)
        assertEquals(domain.dateCreated, view.dateCreated)
        assertEquals(domain.ownerName, view.ownerName)
        assertEquals(domain.ownerAvatar, view.ownerAvatar)
        assertEquals(domain.isBookmarked, view.isBookmarked)
    }


}