package sg.edu.nus.iss.paf_day24workshop_jul2023.repo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_day24workshop_jul2023.model.OrderDetails;

@Repository
public class OrderDetailsRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String insertSQL = "insert into order_details (product, unit_price, discount, quantity, order_id) values (?, ?, ?, ?, ?)";

    // day22 slide 17
    public int[] add(List<OrderDetails> orderDetails) {
        List<Object[]> params = orderDetails.stream().map(od -> new Object[] { od.getProduct(), od.getUnitPrice(), od.getDiscount(), od.getQuantity(), od.getOrderId() }).collect(Collectors.toList());

        int added[] = jdbcTemplate.batchUpdate(insertSQL, params);

        return added;
    }

}
