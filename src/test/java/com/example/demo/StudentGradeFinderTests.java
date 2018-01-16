package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;



@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
public class StudentGradeFinderTests {
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void contextLoads() {
	}
	@Test
	public void getPingTest() throws Exception {
		MvcResult result = mvc.perform(get("/ping")).andReturn(); 
		assertThat(result.getResponse().getContentAsString()).isEqualTo("pong!");
	}
	//Test for optional given date, should take current date
	@Test
	public void getGradeTest1() throws Exception {
		//Assume this year is 2018
		MvcResult result = mvc.perform(get("/getGradeOnGivenDate?hsDate=2016-06-02")).andReturn(); 
		assertThat(result.getResponse().getContentAsString()).isEqualTo("99");
	}
	//Test for given date and  garduation date are same
	@Test
	public void getGradeTest2() throws Exception {
		MvcResult result = mvc.perform(get("/getGradeOnGivenDate?hsDate=2016-06-02&givenDate=2016-06-02")).andReturn(); 
		assertThat(result.getResponse().getContentAsString()).isEqualTo("12");
	}
	//Test for one year back calendar year before current year starts
	@Test
	public void getGradeTest3() throws Exception {
		MvcResult result = mvc.perform(get("/getGradeOnGivenDate?hsDate=2016-06-02&givenDate=2015-08-31")).andReturn(); 
		assertThat(result.getResponse().getContentAsString()).isEqualTo("11");
	}
	//Test for one year back calendar year on the date of  current year starts
		@Test
		public void getGradeTest4() throws Exception {
			MvcResult result = mvc.perform(get("/getGradeOnGivenDate?hsDate=2016-06-02&givenDate=2015-09-02")).andReturn(); 
			assertThat(result.getResponse().getContentAsString()).isEqualTo("12");
		}
		//Test for one year back calendar year on new year eve
				@Test
				public void getGradeTest5() throws Exception {
					MvcResult result = mvc.perform(get("/getGradeOnGivenDate?hsDate=2016-06-02&givenDate=2015-12-31")).andReturn(); 
					assertThat(result.getResponse().getContentAsString()).isEqualTo("12");
				}
				//Test for on new year 
				@Test
				public void getGradeTest6() throws Exception {
					MvcResult result = mvc.perform(get("/getGradeOnGivenDate?hsDate=2016-06-02&givenDate=2016-01-01")).andReturn(); 
					assertThat(result.getResponse().getContentAsString()).isEqualTo("12");
				}
		
	@Test
    public void getGradeTest7() throws Exception {
        MvcResult result = mvc.perform(get("/getGradeOnGivenDate?hsDate=2016-06-02&givenDate=2000-05-02")).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("-1");
    }
	
}
