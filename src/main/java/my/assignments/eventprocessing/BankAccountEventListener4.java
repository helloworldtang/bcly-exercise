package my.assignments.eventprocessing;

import com.google.common.base.Function;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import my.assignments.domain.BankAccount;
import my.assignments.domain.BankAccountImpl;
import my.assignments.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A delegate class that obtains event based listeners for {@link Subscribe}'d events
 *
 * Created by chrissekaran on 05/02/15.
 */
@Service
public class BankAccountEventListener4 implements InitializingBean {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountEventListener4.class.getSimpleName());

    @Autowired
    private EventBus eventBus;
    
    private static final Function<BankAccountImpl, BankAccount> domainToEventTypeBankAccountConverter = new Function<BankAccountImpl, BankAccount>() {
        @Override
        public BankAccount apply(BankAccountImpl input) {
            return new BankAccount() {
                @Override
                public String getId() {
                    return input.getId();
                }

                @Override
                public List<String> getAlternativeIds() {
                    return input.getAlternativeIds();
                }

                @Override
                public Customer getOwningCustomer() {
                    return new Customer() {
                        @Override
                        public String getID() {
                            return input.getOwningCustomer().getID();
                        }

                        @Override
                        public List<BankAccount> getAccounts() {
                            return null;
                        }

                        @Override
                        public String getName() {
                            return input.getOwningCustomer().getName();
                        }
                    };
                }
            };
        }
    };
    
    @Subscribe
    public void bankAccountEventHandler(BankAccountImpl bankAccount) throws InterruptedException {
        BankAccount account = domainToEventTypeBankAccountConverter.apply(bankAccount);
        TimeUnit.SECONDS.sleep(50);
        LOGGER.warn("{}  Bank Account with id {} has been added for Customer with id {}.", Thread.currentThread(), bankAccount.getId(), bankAccount.getOwningCustomer().getID());
        //TODO Event Processing
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        eventBus.register(this);
    }
}
