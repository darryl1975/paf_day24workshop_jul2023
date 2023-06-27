package sg.edu.nus.iss.paf_day24workshop_jul2023.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Boolean makeOrder(Order order, List<OrderDetails> details) {

        // simulate error before performing any operation
        // if (true) {
        //     throw new IllegalArgumentException("Exception before performing any operation");
        // }

        // 1. create the order and get the returned pk of the created order
        Integer iCreatedOrderID = ordRepo.insertOrder(order);

        // simulate error after creating a new order
        // if (true) {
        //     throw new IllegalArgumentException("Exception after creating a new order");
        // }

        // 2. set the fk for the order details that linked to the created order
        for (OrderDetails od: details) {
            od.setOrderId(iCreatedOrderID);
        }

        // 3. create the order details
        // int [] iAdded = ordDetailsRepo.add(details);
        ordDetailsRepo.add(details);

        // simulate order at the end of transaction after performing 2. & 3.
        // if (true) {
        //     throw new IllegalArgumentException("Exception at the end of transaction after performing 2. & 3.");
        // }

        return true;
    }
}
