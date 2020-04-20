package test;

import dao.EmployeeDAO;
import java.util.List;
import model.Employee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJDBC {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("test/spring-jdbc.xml");
        Employee e = context.getBean("emp", Employee.class);
        EmployeeDAO edao = context.getBean("e_dao", EmployeeDAO.class);
//        System.out.println("Calling insert");
//        
//        for(int i = 0; i < 10; i++){
//            e.setId(i);
//            e.setName("Name"+i);
//            e.setSalary(i*1000);
//            System.out.println("Completed: "+ edao.insert(e));
//        }
//        
//        for(int i = 0; i < 10; i++){
//            e.setId(i);
//            e.setName("Name"+i);
//            e.setSalary(i*1000);
//            System.out.println("Completed: "+ edao.insertByPreparedStatement(e));
//        }
//      

        System.out.println("Inserting by named parameter");
        
        for(int i = 0; i < 10; i++){
            e.setId(i);
            e.setName("Name"+i);
            e.setSalary(i*1000);
            System.out.println("Completed: "+ edao.insertByPreparedStatementNamed(e));
        }
        
        System.out.println("Single string retrieving: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(i+": " + edao.retrieveEmpByID(i));
        }
        System.out.println("Single object retrieving: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(i+": " + edao.retrieveAllByID(i).getSalary());
        }
        
        List<Employee> el = edao.retrieveAll();
        System.out.println("List of object retrieving with size: "+el.size());
        for(int i = 0; i < el.size(); i++){
            System.out.println(i+": "+el.get(i).getId()+" <> "+el.get(i).getName()+" <> "+el.get(i).getSalary());
        }
    }
}
