package distmsgs;

import java.io.Serializable;

/**
 * 用于测试实体类.
 *
 * @author 曾谢波
 * @since 2018年11月3日
 */
public class StudentTest implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "StudentTest [name=" + name + ", age=" + age + "]";
	}

}
