package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.MedixinRepository
import com.example.model.AppointmentStatus
import com.example.model.ConsultationType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Medixin", appName)
  }

  @Test
  fun `verify repository initial doctors and booking flow`() {
    val repository = MedixinRepository()
    val doctors = repository.doctors.value
    assertTrue("Doctors catalog should not be empty", doctors.isNotEmpty())

    val marcus = doctors.first { it.name.contains("Marcus Vance") }
    assertNotNull(marcus)

    // Verify appointment booking
    val booked = repository.bookAppointment(
      doctor = marcus,
      dateText = "Wednesday, Oct 16, 2024",
      timeSlot = "10:15 AM",
      type = ConsultationType.IN_PERSON,
      notes = "Routine cardio wellness"
    )

    assertEquals(AppointmentStatus.CONFIRMED, booked.status)
    assertEquals(marcus.name, booked.doctorName)
    assertTrue(repository.appointments.value.any { it.id == booked.id })
  }
}
