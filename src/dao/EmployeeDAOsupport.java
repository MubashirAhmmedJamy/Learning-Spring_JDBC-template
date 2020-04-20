package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Employee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class EmployeeDAOsupport extends NamedParameterJdbcDaoSupport{
        public int insert(Employee e){
        String sql = "insert into employee (ID,Name,Salary) values ("+e.getId()+",'"+e.getName()+"',"+e.getSalary()+")";
        System.out.println("SQL: "+sql);
        int x = 0;
        try {
            x = getJdbcTemplate().update(sql);
            System.out.println("Inserted successfully from Support");
        } catch (DataAccessException ex) {
            System.out.println("Exception found. Insert operation failed.");
        }
        return x;
    }
    
    public int insertByPreparedStatement(Employee e){
        String sql = "insert into employee (ID,Name,Salary) values (?,?,?)";
        System.out.println("SQL: "+sql);
        int x = 0;
        try {
            x = getJdbcTemplate().update(sql, new Object []{e.getId(),e.getName(),e.getSalary()});
            System.out.println("Inserted successfully from Support");
        } catch (DataAccessException ex) {
            System.out.println("Exception encountered. Insert operation failed.");
        }
        return x;
    }
    
    
    public int insertByPreparedStatementNamed(Employee e){
        String sql = "insert into employee (ID,Name,Salary) values (:i,:n,:s)";
        SqlParameterSource ss = new MapSqlParameterSource("n",e.getName()).addValue("i", e.getId()).addValue("s", e.getSalary());
        
        System.out.println("SQL: "+sql);
        int x = 0;
        try {
            System.out.println(ss.toString() +" | "+getNamedParameterJdbcTemplate().toString());
            x = getNamedParameterJdbcTemplate().update(sql, ss);
            System.out.println("Inserted successfully from Support");
        } catch (DataAccessException ex) {
            System.out.println(ex.getMostSpecificCause());
        }
        return x;
    }
    public String retrieveEmpByID(int id){
        String sql = "select Name from employee where id = ?";
//        System.out.println("SQL: "+sql);
        String name = null;
        try {
            name = getJdbcTemplate().queryForObject(sql, new Object []{id}, String.class);
//            System.out.println("Inserted successfully from Support");
        } catch (DataAccessException ex) {
            System.out.println("Exception encountered. Select operation failed.");
        }
        return name;
    }
    
    public Employee retrieveAllByID(int id){
        String sql = "select * from employee where id = ?";
//        System.out.println("SQL: "+sql);
//        Employee emp = null;
        try {
            return getJdbcTemplate().queryForObject(sql, new Object []{id}, new EmployeeMapper());
//            System.out.println("Inserted successfully from Support");
        } catch (DataAccessException ex) {
            System.out.println("Exception encountered. Select all operation failed");
        }
        return null;
    }
    
    public List<Employee> retrieveAll(){
        String sql = "select * from employee";
//        System.out.println("SQL: "+sql);
//        Employee emp = null;
        try {
            List<Employee> e = getJdbcTemplate().query(sql, new EmployeeMapper());
//            System.out.println(e.get(5).getName());
            return e;
//            System.out.println("Inserted successfully from Support");
        } catch (DataAccessException ex) {
            System.out.println("Exception encountered. Select list operation failed. "+ex.getMostSpecificCause());
        }
        return null;
    }
    
    public class EmployeeMapper implements RowMapper<Employee>{
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee emp = new Employee();
            emp.setId(rs.getInt("ID"));
            emp.setName(rs.getString("Name"));
            emp.setSalary(rs.getDouble("Salary"));
//            System.out.println(emp.getId()+" <> "+emp.getName()+" <> "+emp.getSalary());
            return emp;
        }
    }
}
