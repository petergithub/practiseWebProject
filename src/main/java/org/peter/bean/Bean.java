package org.peter.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * http://stackoverflow.com/questions/14211866/validators-in-spring-mvc
 * 
 * To achieve this behaviour I suggest to use groups with dynamically
 * activation. Look at my example bellow.
 * 
 * Person.java:
 * 
 * <pre>
 * class Person {
 * 
 *     @NotNull(groups = StudentChecks.class)
 *     @Email
 *     private email;
 * 
 *     // other members with getters/setters
 * 
 *     public interface StudentChecks {
 *     }
 * }
 * </pre>
 * 
 * In this case @NotNull constraint will be executed only when StudentChecks
 * group is activated. To activate validation group by condition Spring offers
 * special annotation @Validated.
 * 
 * StudentController.java:
 * 
 * <pre>
 * &#064;Controller
 * public class StudentController {
 * 
 * 	&#064;RequestMapping(value = &quot;/students&quot;, method = RequestMethod.POST)
 * 	public String createStudent(@Validated({ Person.StudentChecks.class }) Person student,
 * 			BindingResult result) {
 * 		// your code
 * 	}
 * 
 * }
 * </pre>
 * 
 * @author Shang Pu
 * @version Date：Nov 17, 2015 10:51:33 AM
 */
public class Bean {

	// only check for group GetBeanCheck which configure with spring like below
	// @Validated({Bean.GetBeanCheck.class}) Bean bean
	@NotNull(groups = GetBeanCheck.class)
	private Long id;
	private String name;
	private String value;
	private Date creationDate;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bean [id=").append(id).append(", name=").append(name).append(", value=")
				.append(value).append(", creationDate=").append(creationDate).append("]");
		return builder.toString();
	}

	public Bean(Long id, String name, String value, Date creationDate) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.creationDate = creationDate;
	}

	public Bean(Long id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public Bean(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public Bean(String name) {
		super();
		this.name = name;
	}

	public Bean() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public interface GetBeanCheck {
	}

}