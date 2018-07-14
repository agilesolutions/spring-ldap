package ch.agilesolutions.ldap;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * https://memorynotfound.com/spring-ldap-spring-boot-embedded-ldap-configuration-example/
 * @author rob
 *
 */
@Service
public class PersonRepository {

    @Autowired
    private LdapTemplate ldapTemplate;

    /**
     * Retrieves all the persons in the ldap server
     * @return list of person names
     */
    public List<String> getAllPersonNames() {
        return ldapTemplate.search(
                query().where("objectClass").is("user").and("sAMAccountName").is("u24279"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return (String) attrs.get("memberOf").get();
                    }
                });
    }

}