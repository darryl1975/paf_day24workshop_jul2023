package sg.edu.nus.iss.paf_day24workshop_jul2023.uicontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.paf_day24workshop_jul2023.model.Order;
import sg.edu.nus.iss.paf_day24workshop_jul2023.model.OrderDetails;
import sg.edu.nus.iss.paf_day24workshop_jul2023.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    OrderService ordSvc;

    @Value("${home.title}") 
    private String homeTitle;

    @GetMapping
    public String welcome(Model model) {
        model.addAttribute("homeTitle", homeTitle);
        return "welcome";
    }

    @GetMapping("/orderadd")
    public String newOrderForm(Model model) {
        Order order = new Order();
        model.addAttribute("order", order);

        return "orderadd";
    }

    @PostMapping("/ordernext")
    public String postOrderForm(HttpSession session, @ModelAttribute("order") Order order) {
        // System.out.println("HomeController > postOrderForm > " + order);
        session.setAttribute("order", order);

        return "redirect:/home/orderdetailadd";
    }

    @GetMapping("/orderdetailadd")
    public String newOrderDetailForm(Model model) {
        OrderDetails orderDetails = new OrderDetails();
        model.addAttribute("orderDetails", orderDetails);

        return "orderdetailadd";
    }

    @PostMapping("/nextdetails")
    public String postOrderDetails(HttpSession session, @ModelAttribute("orderdetails") OrderDetails orderDetails,
            Model model) {
        System.out.println("HomeController > postOrderDetails > " + session.getAttribute("order"));
        System.out.println("HomeController > postOrderDetails > " + orderDetails);

        List<OrderDetails> ordDetailList = null;
        if (session.getAttribute("orderdetails") == null) {
            ordDetailList = new ArrayList<OrderDetails>();
            ordDetailList.add(orderDetails);
            session.setAttribute("orderdetails", ordDetailList);
        } else {
            ordDetailList = (List<OrderDetails>) session.getAttribute("orderdetails");
            ordDetailList.add(orderDetails);
            session.setAttribute("orderdetails", ordDetailList);
        }

        OrderDetails newOrderDetail = new OrderDetails();
        model.addAttribute("orderDetails", newOrderDetail);
        return "orderdetailadd";
    }

    @PostMapping("/completeorder")
    public String completeOrder(HttpSession session, @ModelAttribute("orderdetails") OrderDetails orderDetails) {
        // List<OrderDetails> ordDetailList = null;
        // if (session.getAttribute("orderdetails") == null) {
        //     ordDetailList = new ArrayList<OrderDetails>();
        //     ordDetailList.add(orderDetails);
        //     session.setAttribute("orderdetails", ordDetailList);
        // } else {
        //     ordDetailList = (List<OrderDetails>) session.getAttribute("orderdetails");
        //     ordDetailList.add(orderDetails);
        //     session.setAttribute("orderdetails", ordDetailList);
        // }

        return "redirect:/home/orderreview";
    }

    @GetMapping("/orderreview")
    public String reviewOrder(HttpSession session, Model model) {
        model.addAttribute("order", (Order) session.getAttribute("order"));
        model.addAttribute("orderdetails", (List<OrderDetails>) session.getAttribute("orderdetails"));
        
        return "orderreview";
    }
    @GetMapping("/closeorder")
    public String closeOrder(HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        List<OrderDetails> orderDetails = (List<OrderDetails>) session.getAttribute("orderdetails");
        ordSvc.makeOrder(order, orderDetails);

        // remove the session objects created for the order and orderdetails
        session.invalidate();

        return "welcome";
    }
}
