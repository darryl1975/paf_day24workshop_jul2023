package sg.edu.nus.iss.paf_day24workshop_jul2023.model;

import java.sql.Date;

import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.IntegerDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer orderId;

    private Date orderDate;

    private String customerName;

    private String shipAddress;

    private String notes;

    private Float tax;
}
