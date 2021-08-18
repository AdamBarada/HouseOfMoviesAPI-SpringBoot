package controllers.handleRequests.permitAll;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import config.TokenProvider;
import dto.user.UserLoginDto;
import entities.AuthToken;
import services.user.UserService;

@Controller
@CrossOrigin
@RequestMapping("/token")
@ResponseBody
public class AuthenticationController {		// login

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;
    
    @PostMapping("/generate-token")
    public ResponseEntity<?> register(@RequestBody UserLoginDto loginUser) throws AuthenticationException {
    		final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getEmail(),
                            loginUser.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            return ResponseEntity.ok(new AuthToken(token, userService.isAdmin(loginUser.getEmail())));
        
    }
}
