package org.dhbw.mosbach.ai.tickets.beans;

import org.dhbw.mosbach.ai.tickets.database.UserDAO;
import org.dhbw.mosbach.ai.tickets.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named("userBean")
@SessionScoped
public class UserBean extends AbstractBean {
    private static final long serialVersionUID = -7105806000082771152L;

    @Inject
    private UserDAO userDAO;

    private List<User> users;

    private User currentUser;

    private static final String USER_DETAIL_VIEW = "userDetail";


    @PostConstruct
    public void init() {
        this.users = userDAO.getAllFullyLoaded();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<User> getEditors() {
        return users.stream().filter(user -> user.getRoles().stream().allMatch(role -> role.getName().equals("editor"))).collect(Collectors.toList());
    }

    public String getUserName(long id) {
        if (id == 0) { return "None";}
        else return users.stream().filter(user -> user.getId() == id).collect(Collectors.toList()).get(0).getName();
    }

    public String getUserCompany(long id) {
        return users.stream().filter(user -> user.getId() == id).collect(Collectors.toList()).get(0).getCompany();
    }

    public String userDetail(long id) {
        this.currentUser = users.stream().filter(user -> user.getId() == id).collect(Collectors.toList()).get(0);
        return USER_DETAIL_VIEW;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
