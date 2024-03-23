package com.onedayoffer.taskdistribution;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
class TaskDistributionApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldReturnAllEmployees() throws Exception {
		this.mockMvc.perform(get("/employees"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.*", isA(ArrayList.class)))
				.andExpect(jsonPath("$.*", hasSize(5)))
				.andExpect(jsonPath("$[*].fio", hasItems("Mark", "Boris", "Tamara")));
	}

	@Test
	void shouldReturnAllEmployeesAndASCSort() throws Exception {
		this.mockMvc.perform(get("/employees?sort=ASC"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.*", isA(ArrayList.class)))
				.andExpect(jsonPath("$.*", hasSize(5)))
				.andExpect(jsonPath("$[*].fio",
						contains("Armen", "Boris", "Mark", "Pavel", "Tamara")));
	}

	@Test
	void shouldReturnOneEmployee() throws Exception {
		this.mockMvc.perform(get("/employees/4"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.fio", is("Boris")))
				.andExpect(jsonPath("$.jobTitle", is("hall employee")));
	}

	@Test
	void shouldReturnTasksByEmployeeId() throws Exception {
		this.mockMvc.perform(get("/employees/4/tasks"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.*", isA(ArrayList.class)))
				.andExpect(jsonPath("$.*", hasSize(5)))
				.andExpect(jsonPath("$[*].name",
						contains("task-for-boris-1", "task-for-boris-2",
								"task-for-boris-3", "task-for-boris-4",
								"task-for-boris-5")));
	}

	@Test
	void shouldChangeTaskStatus() throws Exception {
		this.mockMvc.perform(get("/employees/5/tasks"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.*", isA(ArrayList.class)))
				.andExpect(jsonPath("$.*", hasSize(1)))
				.andExpect(jsonPath("$[0].status", is("IN_PROGRESS")))
				.andExpect(jsonPath("$[0].id", is(13)));

		this.mockMvc.perform(patch("/employees/5/tasks/13/status")
				.queryParam("newStatus","DONE"))
				.andDo(print())
				.andExpect(status().isOk());

		this.mockMvc.perform(get("/employees/5/tasks"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.*", isA(ArrayList.class)))
				.andExpect(jsonPath("$.*", hasSize(1)))
				.andExpect(jsonPath("$[0].status", is("DONE")))
				.andExpect(jsonPath("$[0].id", is(13)));
	}

	@Test
	void shouldPostNewTask() throws Exception {
		String request = "{ \"name\": " +
				"\"new-task-123\", " +
				"\"taskType\": " +
				"\"INVENTORY\", " +
				"\"status\": " +
				"\"APPOINTED\", " +
				"\"priority\": " +
				"2, " +
				"\"leadTime\": " +
				"8 }";

		this.mockMvc.perform(get("/employees/2/tasks"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.*", isA(ArrayList.class)))
				.andExpect(jsonPath("$.*", hasSize(3)));

		this.mockMvc.perform(post("/employees/2/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andDo(print())
				.andExpect(status().isCreated());

		this.mockMvc.perform(get("/employees/2/tasks"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.*", isA(ArrayList.class)))
				.andExpect(jsonPath("$.*", hasSize(4)));
	}
}
