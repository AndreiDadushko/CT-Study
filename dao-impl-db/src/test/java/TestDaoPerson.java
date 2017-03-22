import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.andreidadushko.tomography2017.dao.impl.db.IPersonDao;
import com.andreidadushko.tomography2017.datamodel.Person;


public class TestDaoPerson {
	public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dao-context.xml");

        IPersonDao service = context.getBean(IPersonDao.class);
        Person person=new Person();
       
        person.setFirstName("Вика");
        person.setMiddleName("Попова");
        person.setLastName("Врука");
        person.setBirthDate(new Date());
        person.setPhoneNumber("+45345534534");
        person.setAdress("Минск");
        person.setLogin("coolgay");
        person.setPassword("cool");
       
        
        Person p1=service.get(service.insert(person).getId());
        System.out.println(p1);
        
        context.close();
    }
}
