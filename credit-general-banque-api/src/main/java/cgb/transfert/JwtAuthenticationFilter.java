//inutile Intégré dans la class inner de la config....
package cgb.transfert;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cgb.transfert.service.JwtService;
import cgb.transfert.service.MyUserDetailsService;

import java.io.IOException;

//Pas géré comme un @Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private  JwtService jwtService;
	

    private  MyUserDetailsService userDetailsService;
    //Pour régler les problèmes de circularité, Pas d'injection, construit à la main dans la config
    
    
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void setUserDetailsService(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
/*
   // @Autowired
    private final JwtService jwtService;

   // @Autowired
    private final UserAuthService userDetailsService;
    
    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService,UserAuthService userDetailsService ) {
    	this.jwtService=jwtService;
    	this.userDetailsService=userDetailsService;
    }
    */

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        System.out.print("Utilisateur :"+ username + "  -jeton :"+ jwt );
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	//UserDetail obtenu par le service.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            System.out.print("userDetails : "+userDetails.getAuthorities().stream().findFirst().toString() );
            
            if (jwtService.isTokenValid(jwt, userDetails)) {
            	//Token avec détails
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
           
            }
        }

        filterChain.doFilter(request, response);
    }
}
