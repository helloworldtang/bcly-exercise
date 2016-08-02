package my.assignments.service;

import com.google.common.eventbus.EventBus;
import my.assignments.domain.BankAccountImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

/**
 * Created by MyWorld on 2016/8/2.
 */
@Service
//@DependsOn({"eventBus"})
public class EventBusTest {
    @Autowired
    private EventBus eventBus;

    public void addAccount(BankAccountImpl bankAccount) {
        eventBus.post(bankAccount);
    }
}
