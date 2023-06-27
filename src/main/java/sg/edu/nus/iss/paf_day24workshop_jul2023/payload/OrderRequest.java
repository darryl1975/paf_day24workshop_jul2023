package sg.edu.nus.iss.paf_day24workshop_jul2023.payload;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.nus.iss.paf_day24workshop_jul2023.model.Order;
import sg.edu.nus.iss.paf_day24workshop_jul2023.model.OrderDetails;

@Data
@NoArgsConstructor
public class OrderRequest {
    
    private Order order;

    private List<OrderDetails> orderdetails;
}
