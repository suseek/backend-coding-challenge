package com.engagetech.expenses.web

import com.engagetech.expenses.ExpensesApplication
import com.engagetech.expenses.service.ExpenseSeparator
import com.engagetech.expenses.service.ExpenseService
import groovy.json.JsonBuilder
import ma.glasnost.orika.MapperFacade
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.transaction.Transactional

import static com.engagetech.expenses.web.ApiParameters.API_V1
import static com.engagetech.expenses.web.ApiParameters.EXPENSES
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@ContextConfiguration(classes = ExpensesApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@ActiveProfiles("integration-test")
class ExpenseControllerTest extends Specification {

	def separator = new ExpenseSeparator()
	def mapper = Mock(MapperFacade)
	def expenseService = Mock(ExpenseService)
	def controller = new ExpenseController(expenseService, mapper, separator)
	MockMvc mockMvc = standaloneSetup(controller).build()

	def "should make a post call for adding new expense"() {
		given:
			final builder = new JsonBuilder()
			builder {
				amount "100"
				date "10/05/2017"
				reason "Now that's a fine test!"
			}
		when:
			final savedReviewResponse = mockMvc.perform(
					post(API_V1 + EXPENSES)
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(builder.toPrettyString()))
					.andReturn().response

		then:
			savedReviewResponse.status == 200
	}
}
