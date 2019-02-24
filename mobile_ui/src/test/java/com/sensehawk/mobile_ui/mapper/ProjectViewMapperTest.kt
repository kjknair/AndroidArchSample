package com.sensehawk.mobile_ui.mapper

import com.sensehawk.mobile_ui.test.factory.ProjectFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectViewMapperTest {

    private val mapper = ProjectViewMapper()

    @Test
    fun mapToViewMapsData() {
        val view = ProjectFactory.makeProjectView()
        val model = mapper.mapToView(view)
        assertEquals(view.id, model.id)
        assertEquals(view.name, model.name)
        assertEquals(view.fullName, model.fullName)
        assertEquals(view.starCount, model.starCount)
        assertEquals(view.dateCreated, model.dateCreated)
        assertEquals(view.ownerName, model.ownerName)
        assertEquals(view.ownerAvatar, model.ownerAvatar)
        assertEquals(view.isBookmarked, model.isBookmarked)
    }


}