/**
 * 
 */
package com.metacube.ipathshala.dao;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * @author amit.c
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:ipathshala-db.xml"})
public class EmployeeServiceTest extends TestCase {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Resource
	private EmployeeDao employeeDao;

	@Resource
	private DepartmentDao departmentDao;

	@Resource
	private ProjectDao projectDao;

	@Override
	@Before
	public void setUp() {
		helper.setUp();
	}

	@Override
	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testCreateEmployee() {

		Employee emp = createEmployee();
		System.out.println(emp.getId());
	}

	@Test
	public void updateEmployeeDepartment() {

		Employee emp = createEmployee();

		Department dept = new Department();
		dept.setName("RAW");

		emp.setDepartment(dept);

		emp = employeeDao.update(emp);

		Department newDept = departmentDao.get(emp.getDepartmentKey());

		assertEquals("RAW", newDept.getName());

	}

	private Employee createEmployee() {
		Employee emp = new Employee();

		emp.setName("Lorenzo Zambernardi");

		Department dept = new Department();

		dept.setName("External Affairs");
		dept = departmentDao.create(dept);

		emp.setDepartment(dept);

		Project p1 = new Project();
		p1.setProjectName("Oil Spot");
		p1.setProjectType("insurgency");

		Project p2 = new Project();
		p1.setProjectName("Cordon and search");
		p1.setProjectType("military tactic");

		p1 = projectDao.create(p1);
		p2 = projectDao.create(p2);

		emp.addProject(p1);
		emp.addProject(p2);

		emp = employeeDao.create(emp);
		return emp;
	}

	/**
	 * @return the employeeDao
	 */
	public final EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	/**
	 * @param employeeDao
	 *            the employeeDao to set
	 */
	public final void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	/**
	 * @return the departmentDao
	 */
	public final DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	/**
	 * @param departmentDao
	 *            the departmentDao to set
	 */
	public final void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	/**
	 * @return the projectDao
	 */
	public final ProjectDao getProjectDao() {
		return projectDao;
	}

	/**
	 * @param projectDao
	 *            the projectDao to set
	 */
	public final void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
