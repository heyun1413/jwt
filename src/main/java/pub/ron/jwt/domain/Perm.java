package pub.ron.jwt.domain;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 权限实体
 * @author ron
 * 2019.01.03
 */
@Entity
@Table
public class Perm extends BaseEntity {


    @NotNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String>  uriPatterns;

    @ElementCollection
    @CollectionTable(name = "request_method",
            joinColumns = @JoinColumn( name = "permission_id"))
    @Column(name = "method")
    @Enumerated(EnumType.STRING)
    private Set<RequestMethod> methods;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Set<String> getUriPatterns() {
        return uriPatterns;
    }

    public void setUriPatterns(Set<String> uriPatterns) {
        this.uriPatterns = uriPatterns;
    }

    public Set<RequestMethod> getMethods() {
        return methods;
    }

    public void setMethods(Set<RequestMethod> methods) {
        this.methods = methods;
    }

}
