package my.assignments.web;

import my.assignments.domain.BankAccountImpl;
import my.assignments.service.DataService;
import my.assignments.service.EventBusTest;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by chrissekaran on 04/02/15.
 */
@RestController
public class BankingController {

    @Autowired
    private DataService dataService;

    @Autowired
    private EventBusTest eventBusTest;

    public BankingController() {
    }

    //@Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Display a {@link my.assignments.domain.CustomerImpl} by Id
     *
     * @param id for the {@link my.assignments.domain.CustomerImpl}
     * @return a {@link my.assignments.domain.CustomerImpl}
     */
    @RequestMapping(value = "/customer/{id}", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    String customer(@PathVariable String id) {
        return dataService.customerById(id).toString();
    }

    /**
     * To add a {@link my.assignments.domain.BankAccountImpl} to the {@link my.assignments.domain.CustomerImpl}
     *
     * @param bankAccountString
     * @return the {@link my.assignments.domain.CustomerImpl} object pertaining to the {@link my.assignments.domain.BankAccountImpl}
     */
    @RequestMapping(value = "/customer/", //produces = "application/json", consumes = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    String addAccountForCustomer(@RequestBody String bankAccountString) {
        String test = "{\"id\":\""+bankAccountString+"\",\"owningCustomer\":{\"id\":\"1001\",\"name\":\"John Lennon\"}}";
        BankAccountImpl bankAccount = null;
        try {
            bankAccount = objectMapper.readValue(test, BankAccountImpl.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        eventBusTest.addAccount(bankAccount);
        return "Success";
    }

}
