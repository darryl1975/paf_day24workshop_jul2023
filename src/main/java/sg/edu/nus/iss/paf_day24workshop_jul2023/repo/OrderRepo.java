package sg.edu.nus.iss.paf_day24workshop_jul2023.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_day24workshop_jul2023.exception.ResourceNotFoundException;
import sg.edu.nus.iss.paf_day24workshop_jul2023.model.Order;

@Repository
public class OrderRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String findAllSQL = "select * from orders";
    private final String findByIdSQL = "select * from orders where order_id = ?";
    private final String insertSQL = "insert into orders (order_date, customer_name, ship_address, notes, tax) values (?, ?, ?, ?, ?)";

    public Order findOrderById(Integer orderId) {
        List<Order> orders = jdbcTemplate.query(findByIdSQL, BeanPropertyRowMapper.newInstance(Order.class), orderId);

        if (orders.isEmpty()) {
            // throw custom exception
            throw new ResourceNotFoundException("Order not found");
        }

        return orders.get(0);
    }

    public List<Order> findAllOrders() {
        List<Order> orders = jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper.newInstance(Order.class));

        if (orders.isEmpty()) {
            // throw custom exception
            throw new ResourceNotFoundException("Order not found");
        }

        return orders;
    }

    public Boolean createOrder(Order order) {
        // "insert into orders (order_date, customer_name, ship_address, notes, tax) values (?, ?, ?, ?, ?)"
        Integer iResult = jdbcTemplate.update(insertSQL, order.getOrderDate(), order.getCustomerName(), order.getShipAddress(), order.getNotes(), order.getTax());

        return iResult > 0 ? true : false;
    }

    public Integer insertOrder(Order order) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertSQL, new String[] { "order_id" });
                ps.setDate(1, order.getOrderDate());
                ps.setString(2, order.getCustomerName());
                ps.setString(3, order.getShipAddress());
                ps.setString(4, order.getNotes());
                ps.setFloat(5, order.getTax());
                return ps;
            }
            
        };
        
        jdbcTemplate.update(psc, generatedKeyHolder);

        Integer iReturnValue = generatedKeyHolder.getKey().intValue();
        return iReturnValue;
    }

}
