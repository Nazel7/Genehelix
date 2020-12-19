package com.genehelix.controllers.views;

import com.genehelix.entities.Customer;
import com.genehelix.entities.Review;
import com.genehelix.interfaces.IEmployeeService;
import com.genehelix.utils.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewController {
    @Autowired
    private IEmployeeService IEmployeeService;

    private List<String> reviewList;

    public ReviewController() {
        this.reviewList = new ArrayList<>();
    }

    @GetMapping("/reviews/showFormForAddReview")
    public String formForCustomerReview(@RequestParam("customerId") int customerId, Model model) {
        Review review = new Review();
        model.addAttribute("newReview", review);
        model.addAttribute("customerId", customerId);
        return "add-new-review";
    }

    @PostMapping("/reviews/postCustomerReview")
    public String postCustomerReview(@ModelAttribute("newReview") Review review,
                                     @RequestParam("customerId") int customerId) {
        System.out.println("ReviewCustomerId: " + customerId);
        Customer customer = IEmployeeService.getCustomerById(customerId);
        if (customer != null) {
            review.setCustomer(customer);
            IEmployeeService.addNewReview(review);
        }
        return "redirect:/company-employees/employee-list";
    }

    @GetMapping("reviews/showFormForEmployeeCustomerReview")
    public String showEmployeeCustomerReview(@RequestParam("customerReview") int customerId, Model model) {
        reviewList = IEmployeeService.showCustomerReviewList(customerId);
        model.addAttribute("customerId", customerId);

        return ErrorMessageUtil.errorMessage(reviewList,
                "There is no review found....",
                "review-not-found",
                "review-list", model,
                "emptyReview",
                "reviews"
        );

    }

    @GetMapping("reviews/showEmployeeReview")
    public String reviewList(@RequestParam("showReviews") int employeeID, Model model) {
        System.out.println("wpe=" + employeeID);

        reviewList = IEmployeeService.showReviews(employeeID);
        return ErrorMessageUtil.errorMessage(reviewList,
                "There is no review found.....",
                "empty-review-home",
                "review-list", model,
                "emptyReview",
                "reviews"
        );
    }

}
