package pub.ron.jwt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pub.ron.jwt.domain.Permission;
import pub.ron.jwt.domain.Role;
import pub.ron.jwt.domain.User;
import pub.ron.jwt.repository.PermissionRepository;
import pub.ron.jwt.repository.RoleRepository;
import pub.ron.jwt.repository.UserRepository;
import pub.ron.jwt.service.UserService;
import pub.ron.jwt.web.UserController;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtApplicationTests {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

//    @Before
    public void initData() {
        Set<Permission> permissions = new HashSet<>();
        for (String permission : new String[] {"add", "delete", "update", "query"}) {
            permissions.add(new Permission(permission));

        }
        permissionRepository.saveAll(permissions);
        Role role = new Role("admin");
        role.setPermissions(permissions);
        roleRepository.save(role);

        User user = new User();
        user.setRealName("ron");
        user.setUsername("ron");
        user.setPassword("123");
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userService.register(user);
    }

    @Test
    public void testAuthc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();

        mockMvc.perform(post("/user/authc")
                .param("username", "ron")
                .param("password", "123")
        ).andExpect(status().isOk());

        mockMvc.perform(post("/user/authc")
                .param("username", "ron")
                .param("password", "124")
        ).andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

}

