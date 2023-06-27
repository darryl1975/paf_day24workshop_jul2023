package sg.edu.nus.iss.paf_day24workshop_jul2023.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.paf_day24workshop_jul2023.model.Order;
import sg.edu.nus.iss.paf_day24workshop_jul2023.model.OrderDetails;
import sg.edu.nus.iss.paf_day24workshop_jul2023.repo.OrderDetailsRepo;
import sg.edu.nus.iss.paf_day24workshop_jul2023.repo.OrderRepo;

@Service
public class OrderService {
    
    @Autowired
    OrderRepo ordRepo;

    @Autowired
    OrderDetailsRepo ordDetailsRepo;

    public Boolean makeOrder(Order order, List<OrderDetails> details) {

        // 1. create the order and get the returned pk of the created order
        Integer iCreatedOrderID = ordRepo.insertOrder(order);

        // 2. set the fk for the order details that linked to the created order
        for (OrderDetails od: details) {
            od.setOrderId(iCreatedOrderID);
        }

        // 3. create the order details
        // int [] iAdded = ordDetailsRepo.add(details);
        ordDetailsRepo.add(details);

        return true;
    }
}
