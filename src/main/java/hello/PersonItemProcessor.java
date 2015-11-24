package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);
    
    private static String failName = "Joe";

    private RetryTemplate retryTemplate;
    
	public PersonItemProcessor(RetryTemplate retryTemplate) {
		super();
		this.retryTemplate = retryTemplate;
	}

	@Override
    public Person process(final Person person) throws Exception {
		return retryTemplate.execute(new RetryCallback<Person, Exception>() {
			public Person doWithRetry(RetryContext context){
				return transformPerson(person);
			}
		});
	}
	
	//Silly method to stand in for some sort of failing service that we call.
	private Person transformPerson(Person person)
	{
    	if(failName.equals(person.getFirstName()))
    	{
    		failName = "";
    		throw new RuntimeException("Failing the first time through because of Joe");
    	}
		
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();
        final int age = person.getAge();

        final Person transformedPerson = new Person(firstName, lastName, age);

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
	}

}
