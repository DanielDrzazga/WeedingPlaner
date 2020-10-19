package weddingplanner.application.component;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Create by Daniel Drzazga on 19.10.2020
 **/

@Getter
@Setter
@Component
@SessionScope
public class CurrentUser {

    private Long id;

    private String firstName;

    private String lastName;

    private long fakeId = 0;

    public synchronized long reset(){
        return 0;
    }

    public synchronized long inc(){
        return ++fakeId;
    }

    public synchronized long dec(){
        return --fakeId;
    }

    public synchronized void setFakeId(Long fakeId){
        this.fakeId = fakeId;
    }
}
