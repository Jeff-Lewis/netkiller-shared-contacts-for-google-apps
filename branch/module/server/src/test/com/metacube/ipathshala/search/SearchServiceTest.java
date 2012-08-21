package com.metacube.ipathshala.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.metacube.ipathshala.dao.StudentDao;
import com.metacube.ipathshala.entity.Student;
import com.metacube.ipathshala.search.property.DateSearchProperty;
import com.metacube.ipathshala.search.property.OrderByProperty;
import com.metacube.ipathshala.search.property.SearchProperty;
import com.metacube.ipathshala.search.property.StringSearchProperty;
import com.metacube.ipathshala.search.property.operator.FilterGroupOperatorType;
import com.metacube.ipathshala.search.property.operator.FilterOperatorType;
import com.metacube.ipathshala.search.property.operator.OrderByOperatorType;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:ipathshala-db.xml"})
public class SearchServiceTest {
	
	@Autowired
	SearchService service;
	
	@Autowired
	StudentDao studentDao;
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}
	@Test
	public void testSearchWithNoFiltersNoOrderNoRange() throws Exception{
		Student student = createStudent("Safia","Kremier","San Jose", null);
		studentDao.create(student);
		SearchCriteria searchCriteria = new SearchCriteria(null,Student.class);
		SearchRequest request = new SearchRequest(searchCriteria,null,null,FilterGroupOperatorType.AND,null,SearchRequest.ResultType.Entity);
		SearchResult result=service.doSearch(request);
		List<Object>  results=result.getResultObjects();
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size()>0);
		System.out.println(results.size());
		studentDao.remove(student.getKey());
	}
	
	@Test
	public void testSearchWithFiltersNoOrderNoRange() throws Exception{
		Student student = createStudent("Safia","Kremier","San Jose", null);
		Student student2 = createStudent("Bob","Lopez","Reno", null);
		studentDao.create(student);
		studentDao.create(student2);
		SearchProperty property= new StringSearchProperty("firstName","Safia",FilterOperatorType.EQUAL,false);
		SearchProperty property2= new StringSearchProperty("presentAddressCity","San Jose",FilterOperatorType.EQUAL,false);
		List list = new ArrayList();
		list.add(property);
		list.add(property2);
		SearchCriteria searchCriteria = new SearchCriteria(list,Student.class);
		SearchRequest request = new SearchRequest(searchCriteria,null,null,FilterGroupOperatorType.AND,null,SearchRequest.ResultType.Entity);
		SearchResult result=service.doSearch(request);
		List<Object>  results=result.getResultObjects();
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size()>0);
		Assert.assertEquals(((Student)results.get(0)).getFirstName(), "Safia");
		System.out.println(results.size());
		studentDao.remove(student.getKey());
		studentDao.remove(student2.getKey());
	}
	
	@Test
	public void testSearchWithFiltersAndOrderNoRange() throws Exception{
		Student student = createStudent("Safia","Kremier","Reno", null);
		Student student2 = createStudent("Bob","Lopez","Reno", null);
		studentDao.create(student);
		studentDao.create(student2);
		SearchProperty property= new StringSearchProperty("presentAddressCity","Reno",FilterOperatorType.EQUAL,false);
		List<SearchProperty> filterList = new ArrayList<SearchProperty>();
		filterList.add(property);
	
		
		OrderByProperty op1= new OrderByProperty("firstName",OrderByOperatorType.ASC);
		List<OrderByProperty> orderByList = new ArrayList<OrderByProperty>();
		orderByList.add(op1);
		
		SearchCriteria searchCriteria = new SearchCriteria(filterList,Student.class);
		SearchRequest request = new SearchRequest(searchCriteria,null,orderByList,FilterGroupOperatorType.AND,null,SearchRequest.ResultType.Entity);
		SearchResult result=service.doSearch(request);
		List<Object>  results=result.getResultObjects();
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size()>0);
		//bob should come first and then safia
		Assert.assertEquals(((Student)results.get(0)).getFirstName(), "Bob");
		Assert.assertEquals(((Student)results.get(1)).getFirstName(), "Safia");
		System.out.println(results.size());
		studentDao.remove(student.getKey());
		studentDao.remove(student2.getKey());
	}
	
	@Test
	public void testSearchWithRangeLimit() throws Exception{
		Student student = createStudent("Safia","Kremier","Reno", null);
		Student student2 = createStudent("Bob","Lopez","Reno", null);
		Student student3 = createStudent("Julian","Assange","Perth", null);
		studentDao.create(student);
		studentDao.create(student2);
		studentDao.create(student3);
			
		SearchCriteria searchCriteria = new SearchCriteria(null,Student.class);
		FilterRecordRange filterRange= new FilterRecordRange(1,3);
		SearchRequest request = new SearchRequest(searchCriteria,null,null,FilterGroupOperatorType.AND,filterRange,SearchRequest.ResultType.Entity);
		SearchResult result=service.doSearch(request);
		List<Object>  results=result.getResultObjects();
		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() == 2);
		studentDao.remove(student.getKey());
		studentDao.remove(student2.getKey());
		studentDao.remove(student3.getKey());
	}
	
	@Test
	public void testFailureWithMultipleInvalidInequalityFilter() throws Exception{
		Student student = createStudent("Safia","Kremier","San Jose", null);
		Student student2 = createStudent("Bob","Lopez","Reno", null);
		studentDao.create(student);
		studentDao.create(student2);
		SearchProperty property= new StringSearchProperty("firstName","Safia",FilterOperatorType.NOT_EQUAL,false);
		SearchProperty property2= new DateSearchProperty("enrollmentDate",new Date("12/10/2010"),FilterOperatorType.GREATER_THAN);
		List list = new ArrayList();
		list.add(property);
		list.add(property2);
		SearchCriteria searchCriteria = new SearchCriteria(list,Student.class);
		SearchRequest request = new SearchRequest(searchCriteria,null,null,FilterGroupOperatorType.AND,null,SearchRequest.ResultType.Entity);
		try{
		SearchResult result=service.doSearch(request);
		Assert.fail("Exception should be thrown");
		}catch (Exception e) {
		}
		studentDao.remove(student.getKey());
		studentDao.remove(student2.getKey());
	}
	
	@Test
	public void testDateInequalityFilter() throws Exception{
		Student student = createStudent("Safia","Kremier","San Jose", new Date("12/10/2010"));
		Student student2 = createStudent("Bob","Lopez","Reno", new Date("14/10/2010"));
		Student student3 = createStudent("Julian","Assange","Perth", new Date("20/10/2010"));
		studentDao.create(student);
		studentDao.create(student2);
		studentDao.create(student3);
		SearchProperty property= new DateSearchProperty("enrollmentDate",new Date("11/10/2010"),FilterOperatorType.GREATER_THAN);
		SearchProperty property2= new DateSearchProperty("enrollmentDate",new Date("15/10/2010"),FilterOperatorType.LESS_THAN);
		List list = new ArrayList();
		list.add(property);
		list.add(property2);
		OrderByProperty op1= new OrderByProperty("firstName",OrderByOperatorType.ASC);
		List<OrderByProperty> orderByList = new ArrayList<OrderByProperty>();
		orderByList.add(op1);
		SearchCriteria searchCriteria = new SearchCriteria(list,Student.class);
		SearchRequest request = new SearchRequest(searchCriteria,null,orderByList,FilterGroupOperatorType.AND,null,SearchRequest.ResultType.Entity);		
		SearchResult result=service.doSearch(request);
		List<Object>  results=result.getResultObjects();
		Assert.assertTrue(result.getResultsSize() == 2);
		Assert.assertEquals(((Student)results.get(0)).getFirstName(), "Safia");
		Assert.assertEquals(((Student)results.get(1)).getFirstName(), "Bob");
		studentDao.remove(student.getKey());
		studentDao.remove(student2.getKey());
		studentDao.remove(student3.getKey());
	}
	private Student createStudent(String firstName,String lastName,String city, Date dateParam) throws ParseException{
		Student student = new Student();
		SimpleDateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyy");
		if(dateParam!=null){			
			student.setEnrollmentDate(dateParam);
		}
		student.setFirstName(firstName);
		student.setLastName(lastName);
		/*student.setPresentAddressCity(city);*/
		return student;
	}
	
	
}
